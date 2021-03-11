## 205-web-rest-enhanced

最小成本的实现服务接口的rest支持，主要借助`RequestMappingHandlerMapping`来实现自定义的请求映射

<!-- more -->

### 1. 场景说明

如何最小成本的为一个非web服务提供rest接口？ 

什么场景会有上面这种需求场景呢，最近正好遇到了。我们有一个服务，本来是提供gprc的服务接口，结果因为某些原因，现在要提供http接口访问，难不成针对所有的Service都重新写一个对应的RestController，然后再做一层转发么

如果真这样，也着实有点蛋疼，那么有没有什么简单的方式来实现呢？

- 一个拦截器，解析url，通过反射的方式调用
- 借助`RequestMappingHandlerMapping`来实现扩展

接下来将主要介绍第二种方案，自定义url映射规则

### 2. 项目环境

本项目借助`SpringBoot 2.2.1.RELEASE` + `maven 3.5.3` + `IDEA` + `jdk1.8`进行开发

pom核心依赖，web包即可

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 3. 设计方案

实现面向接口的REST服务扩展，通过自定义注解 `RestService` 来扫描哪些API需要生成对应的rest服务

url生成规则

- 只支持POST请求
- url: `service/method`
- 参数: 表单方式提交

举例说明

```java
@RestService
public interface UserApi {

    /**
     * getName by id
     *
     * @param id
     * @return
     */
    String getName(int id);
}
```

上面这个API生成的url访问姿势如下

```bash
curl -X POST 'http://127.0.0.1:8080/UserApi/getName' -d 'id=111'
```

### 4. 实现

整体的实现过程相对简单，两个核心点

#### 4.1 注解定义

自定义一个注解，用于表明哪些接口需要生成REST服务，特别注意，这个注解上还有`@RestController`，不能少

```java
@RestController
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RestService {
}
```

#### 4.2 url映射

核心点，覆盖`getMappingForMethod`，找到当前类or父类上有`RestService`注解的目标类，然后生成`RequestMappingInfo`

```java
public class RestAdapterHandlerMapping extends RequestMappingHandlerMapping {
    @Override
    public Map<RequestMappingInfo, HandlerMethod> getHandlerMethods() {
        return super.getHandlerMethods();
    }

    @Override
    public void registerMapping(RequestMappingInfo mapping, Object handler, Method method) {
        super.registerMapping(mapping, handler, method);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        System.out.println("current handlerType: " + handlerType.getName());
        Class target = getMappingClass(handlerType);
        if (target == null) {
            return super.getMappingForMethod(method, handlerType);
        }

        String prefix = target.getSimpleName();
        RequestMappingInfo info  = createRequestMappingInfo(method, prefix, false);
        if (info != null) {
            return RequestMappingInfo.paths(prefix).build().combine(info);
        }
        return super.getMappingForMethod(method, handlerType);
    }

    private Class getMappingClass(Class<?> handlerType) {
        if (handlerType.isAnnotationPresent(RestService.class)) {
            return handlerType;
        }

        for (Class clz : handlerType.getInterfaces()) {
            if (clz.isAnnotationPresent(RestService.class)) {
                return clz;
            }
        }
        return null;
    }

    protected RequestMappingInfo createRequestMappingInfo(Method method, String prefix, boolean get) {
        RequestMappingInfo.Builder builder =
                RequestMappingInfo.paths(method.getName()).methods(get ? RequestMethod.GET : RequestMethod.POST);
        System.out.println("support url: " + prefix + "/" + method.getName() + (get ? "-X GET" : "-X POST"));
        return builder.build();
    }
}
```

#### 4.3 注册

上面两个就是最核心的地方了，接下来注册一下就完全可用了

```java
@Configuration
@SpringBootApplication
public class Application implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RestAdapterHandlerMapping();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
```

### 5. 测试

接下来测试一下是否能完成我们的目标

一个接口，一个实现Service

```java
@RestService
public interface UserApi {

    String getName(int id);

    String updateName(String user, int age);
}

@Service
public class UserApiImpl implements UserApi {
    Map<String, Integer> cache = new ConcurrentHashMap<>();

    @Override
    public String getName(int id) {
        return "一灰灰blog : " + id;
    }

    @Override
    public String updateName(String user, int age) {
        if (cache.containsKey(user)) {
            Integer old = cache.put(user, age);
            return "update " + user + " old:" + old + " to:" + age;
        }

        cache.put(user, age);
        return "add new: " + user + "| " + age;
    }
}
```

启动之后测试一下

```bash
$ curl -X POST 'http://127.0.0.1:8080/UserApi/updateName' -d 'user=一灰灰Blog&age=18'

add new: 一灰灰Blog| 18
```