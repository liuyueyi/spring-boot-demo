## 204-web-request-proxy

一个自定义实现的面向接口的网络访问实例，主要使用以下知识点:

- ClassPathBeanDefinitionScanner 实现自定义bean扫描
- ImportBeanDefinitionRegistrar 来实现bean注册
- 代理：基于Cglib生成接口的代理类，实现接口增强
- RestTemplate: 实现网络访问

<!-- more -->

### 1. 背景

有使用过`dubbo-rpc`框架的小伙伴应该有这样的感受

- 服务提供方，写API接口，和具体的服务类，
- 消费者通过引入API包，再Spring的生态下，直接`@Autowired`注入接口来实现远程服务访问

如客户端定义一个api如下

```java
@Api
public interface UserApi {
    String getName(int id);

    String updateName(String user, int age);
}
```

对应消费者的使用姿势而言，直接注入即可（这里不展开dubbo的具体使用细节，主要是理解下这个用法）

```java
@Service
public class ConsumerService {
    @Autowired
    private UserApi indexApi;
```

那么我有一个大胆的想法，如果我的项目中有http请求，我是否可以直接定义一个对应的接口，然后实现一个类似上面的使用方式呢？

- 比如大名鼎鼎的 retrofit , openfeign 

如果我们希望自己来实现这样的功能，应该怎么操作呢？

接下来我们以一个最简单、基础的实例，来演示下这个实现思路

部分代码参考自 [https://github.com/LianjiaTech/retrofit-spring-boot-starter](https://github.com/LianjiaTech/retrofit-spring-boot-starter)

### 2. 目标

首先明确以下我们希望实现的效果，我们假定，所有的请求都是POST表单，请求路径由类名 + 方法名来确定，如

```java
@Api
public interface UserApi {
    // 对应的url： /UserApi/getName
    // 访问姿势形如 ： curl -X POST '/UserApi/getName' -d 'id=xxx'
    String getName(int id);
    
    // 对应的url: /UserApi/updateName
    // 访问姿势形如:  curl -X POST '/UserApi/updateName' -d 'user=xxx&age=xxx'
    String updateName(String user, int age);
}
```

使用姿势直接像本地方法调用一样

```java
@RestController
public class DemoRest {
    @Autowired
    private UserApi indexApi;

    @GetMapping
    public String call(String name, Integer age) {
        String ans = indexApi.updateName(name, age);
        String a2 = indexApi.getName(1);
        return ans + " | " + a2;
    }
}
```

### 3. 实现方式

明确上面的目标之后，接下来的实现，第一步相对清晰，哪些接口是需要生成代理对象的呢？

#### 3.1 @Api

定义一个`Api`注解，用来修饰需要接口类，表示这些接口需要生成代理类，通过Http的方法访问

```java
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {
}
```

#### 3.2 接口扫描

下一步就是要扫描项目把接口上有`@Api`注解的都捞出来，需要创建代理类并注册到Spring容器

我们这里借助`ClassPathBeanDefinitionScanner`来实现扫描

```java
@Slf4j
public class ApiScanner extends ClassPathBeanDefinitionScanner {
    private final ClassLoader classLoader;

    public ApiScanner(BeanDefinitionRegistry registry, ClassLoader classLoader) {
        super(registry, false);
        this.classLoader = classLoader;
        registerFilter();
    }

    public void registerFilter() {
        // 表示要过滤出带有 Api 注解的类
        addIncludeFilter(new AnnotationTypeFilter(Api.class));
    }

    // 扫描包下待有 `@Api` 注解的接口，调用 processBeanDefinitions() 实现接口代理类生成注册
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            logger.warn("No @Api interface was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    /**
     * 重写候选判断逻辑，捞出带有注解的接口
     *
     * @param beanDefinition
     * @return
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        if (beanDefinition.getMetadata().isInterface()) {
            try {
                Class<?> target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(), classLoader);
                return !target.isAnnotation();
            } catch (Exception ex) {
                logger.error("load class exception:", ex);
            }
        }
        return false;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            if (logger.isDebugEnabled()) {
                logger.debug("Creating ApiClient with name '" + holder.getBeanName()
                        + "' and '" + definition.getBeanClassName() + "' Interface");
            }
            definition.getConstructorArgumentValues().addGenericArgumentValue(Objects.requireNonNull(definition.getBeanClassName()));
            // beanClass全部设置为ApiFactoryBean
            definition.setBeanClass(ApiFactoryBean.class);
        }
    }
}
```

上面的实现中，有几个细节需要注意

- `addIncludeFilter(new AnnotationTypeFilter(Api.class));` 只注册了一个根据`@Api`注解进行过滤的Filter
- `doScan`: 扫描包，捞出满足条件的类
    - `isCandidateComponent`: 覆盖了父类的判断，用于过滤出我们需要的目标接口，没有它的话会发现捞出来的是一个空集合
    - `processBeanDefinitions`: 针对捞出来的目标，指定FactoryBean(由它来创建bean对象)，构造方法的参数为BeanClass
    
#### 3.3 代理工厂类

上面再注册bean的时候，主要是借助FactoryBean来实现的，我们这里实现一个`ApiFactoryBean`，来负责为接口生成代理的访问类，再内部使用RestTemplate来执行POST请求

```java
public class ApiFactoryBean<T> implements FactoryBean<T> {
    private Class<T> targetClass;

    public ApiFactoryBean(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return ProxyUtil.newProxyInstance(targetClass, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equalsIgnoreCase("toString")) {
                    return method.invoke(proxy, args);
                }

                // 每次访问都创建了要给RestTemplate，可以考虑直接使用容器的bean对象, 方便与ribbon集成，实现负载均衡 
                RestTemplate restTemplate = new RestTemplate();
                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

                for (int index = 0; index < args.length; index++) {
                    Parameter p = method.getParameters()[index];
                    params.add(p.getName(), args[index]);
                }
    
                // 这里用于演示host是写死的，可以考虑根据配置来加载（比如 @Api 中指定host，或者 配置参数方式）
                String url = "http://127.0.0.1:8080/" + targetClass.getSimpleName() + "/" + method.getName();
                String response = restTemplate.postForObject(url, params, String.class);
                if (method.getReturnType() == String.class) {
                    return response;
                }

                return JSONObject.parseObject(response, method.getReturnType());
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return targetClass;
    }
}
``` 

代理类的实现中，有几个可以优化的地方

- `restTemplate`: 可以结合ribbon使用，从而实现更友好的负载策略
- `host`: 上面是直接写死的，推荐采用配置化策略来替代（最简单的就是在application.yml文件中加一个api.host的参数，从它来获取，项目源码中给出了实例）

代理生成工具类

```java
public class ProxyUtil {
    public static <T> T newProxyInstance(Class<?> targetClass, InvocationHandler invocationHandler) {
        if (targetClass == null) {
            return null;
        } else {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(targetClass);
            enhancer.setUseCache(true);
            enhancer.setCallback(new ProxyUtil.SimpleMethodInterceptor(invocationHandler));
            return (T) enhancer.create();
        }
    }

    private static class SimpleMethodInterceptor implements MethodInterceptor, Serializable {
        private transient InvocationHandler invocationHandler;

        public SimpleMethodInterceptor(InvocationHandler invocationHandler) {
            this.invocationHandler = invocationHandler;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return this.invocationHandler.invoke(o, method, objects);
        }
    }
}
```

#### 3.4 bean注册

Scanner通常配合Register使用，实现bean的注册

```java
@Slf4j
public class ApiRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanFactoryAware, BeanClassLoaderAware {
    private ResourceLoader resourceLoader;
    private BeanFactory beanFactory;
    private ClassLoader classLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> packages = getPackagesToScan(importingClassMetadata);
        if (log.isDebugEnabled()) {
            packages.forEach(pkg -> log.debug("Using auto-configuration base package '{}'", pkg));
        }

        ApiScanner apiScanner = new ApiScanner(registry, classLoader);
        if (resourceLoader != null) {
            apiScanner.setResourceLoader(resourceLoader);
        }

        apiScanner.scan(packages.toArray(new String[0]));
    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(com.git.hui.boot.web.anno.ApiScanner.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");

        Set<String> packagesToScan = new LinkedHashSet<>(Arrays.asList(basePackages));
        for (Class clz : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(clz));
        }

        if (packagesToScan.isEmpty()) {
            packagesToScan.add(ClassUtils.getPackageName(metadata.getClassName()));
        }

        return packagesToScan;
    }
}
```

最后自定义一个扫描注解，让上面的Register生效

```java
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(ApiRegister.class)
public @interface ApiScanner {
    @AliasFor("basePackages") String[] value() default {};

    @AliasFor("value") String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}
```

#### 3.5 测试

上面就完成了我们的预期目标，接下来写一个demo测试一下

定义一个api，以及提供rest的Controller

**项目1**

启用端口号 8080

```java
@Api
public interface UserApi {
    String getName(int id);

    String updateName(String user, int age);
}


@RestController
@RequestMapping(path = "UserApi")
public class UserRest implements UserApi {
    @Override
    @RequestMapping(path = "getName")
    public String getName(int id) {
        return "一灰灰blog: " + id;
    }

    @Override
    @PostMapping(path = "updateName")
    public String updateName(String user, int age) {
        return "update " + user + " age to: " + age;
    }
}
```

**项目2**

UserApi接口使用姿势，启用端口号 8081

```java
@RestController
public class DemoRest {
    @Autowired
    private UserApi indexApi;

    @GetMapping
    public String call(String name, Integer age) {
        String ans = indexApi.updateName(name, age);
        String a2 = indexApi.getName(1);
        return ans + " | " + a2;
    }
}
```

测试访问:

```bash
curl 'http://127.0.0.1:8081?name=yihui&age=18'

## 输出日志如下
update yihui age to: 18 | 一灰灰blog: 1
```