## 010-auto-inject

### 项目说明

本项目介绍如何实现一个自定义的`@Autowired`，实现依赖服务注入

主要知识点:

- `BeanPostProcessor`
- 代理类创建

### 1. 代理封装类

借助Spring的`Enhance`来实现代理类生成，比如一个基础的工具类如下

```java
public class ProxyUtil {
    public static <T> T newProxyInstance(Class<?> targetClass, InvocationHandler invocationHandler,
                                         ProxyUtil.CallbackFilter filter) {
        if (targetClass == null) {
            return null;
        } else {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(targetClass);
            enhancer.setUseCache(true);
            enhancer.setCallback(new ProxyUtil.SimpleMethodInterceptor(invocationHandler, filter));
            // 无参构造方法  
            return (T) enhancer.create();
        }
    }

    public interface CallbackFilter {
        boolean accept(Method var1);
    }

    private static class SimpleMethodInterceptor implements MethodInterceptor, Serializable {
        private transient InvocationHandler invocationHandler;
        private transient ProxyUtil.CallbackFilter filter;

        public SimpleMethodInterceptor(InvocationHandler invocationHandler, ProxyUtil.CallbackFilter filter) {
            this.invocationHandler = invocationHandler;
            this.filter = filter;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return this.filter.accept(method) ? this.invocationHandler.invoke(o, method, objects) : methodProxy.invokeSuper(o, objects);
        }
    }
}
```

### 2. 自定义注解

参照`@Autowired`的定义，实现一个自定义的注解（缩减版）

```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoInject {
}
```

### 3. 自定义注入

实现`BeanPostProcessor`，在bean初始化之后，扫描`field/method`，为了做一个区分，下面创建一个代理类，注入依赖

```java
@Component
public class AutoInjectPostProcessor implements BeanPostProcessor {

    private ApplicationContext applicationContext;

    public AutoInjectPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        do {
            for (final Field field : clazz.getDeclaredFields()) {
                final AutoInject annotation = AnnotationUtils.findAnnotation(field, AutoInject.class);
                if (annotation != null) {
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, bean, processInjectionPoint(field.getType()));
                }
            }
            for (final Method method : clazz.getDeclaredMethods()) {
                final AutoInject annotation = AnnotationUtils.findAnnotation(method, AutoInject.class);
                if (annotation != null) {
                    final Class<?>[] paramTypes = method.getParameterTypes();
                    if (paramTypes.length != 1) {
                        throw new BeanDefinitionStoreException(
                                "Method " + method + " doesn't have exactly one parameter.");
                    }
                    ReflectionUtils.makeAccessible(method);
                    ReflectionUtils.invokeMethod(method, bean,
                            processInjectionPoint(paramTypes[0]));
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return bean;
    }

    // 创建代理类，在具体方法执行前后输出一个日志
    protected <T> T processInjectionPoint(final Class<T> injectionType) {
        return ProxyUtil.newProxyInstance(injectionType, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("do before " + method.getName() + " | " + Thread.currentThread());
                try {
                    Object obj = applicationContext.getBean(injectionType);
                    return method.invoke(obj, args);
                } finally {
                    System.out.println("do after " + method.getName() + " | " + Thread.currentThread());
                }
            }
        }, new ProxyUtil.CallbackFilter() {
            @Override
            public boolean accept(Method var1) {
                return true;
            }
        });
    }
}
```

### 4. 测试

接下来验证一下自定义注入方式

```java
@Component
public class DemoService {

    public int calculate(int a, int b) {
        doBefore();
        return a + b;
    }

    private void doBefore() {
        System.out.println("-------- inner ----------: " + Thread.currentThread());
    }
}

@Component
public class DemoService2 {

    public int calculate(int a, int b) {
        doBefore();
        return a + b;
    }

    private void doBefore() {
        System.out.println("-------- inner ----------: " + Thread.currentThread());
    }
}

@Service
public class RestService {
    @AutoInject
    private DemoService demoService;

    private DemoService2 demoService2;

    @AutoInject
    public void setDemoService2(DemoService2 demoService2) {
        this.demoService2 = demoService2;
    }

    public void test() {
        int ans = demoService.calculate(10, 20);
        System.out.println(ans);

        ans = demoService2.calculate(11, 22);
        System.out.println(ans);
    }
}
```

执行完毕之后，输出日志如

```
do before calculate | Thread[main,5,main]
-------- inner ----------: Thread[main,5,main]
do after calculate | Thread[main,5,main]
30

do before calculate | Thread[main,5,main]
-------- inner ----------: Thread[main,5,main]
do after calculate | Thread[main,5,main]
33
```