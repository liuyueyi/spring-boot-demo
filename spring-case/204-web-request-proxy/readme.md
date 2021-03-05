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
