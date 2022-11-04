## 103-instantiation-bean-post-processor

bean的实例化、初始化、注入前后的扩展点

核心知识点：

- `InstantiationAwareBeanPostProcessor`

几个方法的执行顺序

- postProcessBeforeInstantiation：实例化bean之前，相当于new这个bean之前
- bean的构造方法
- postProcessAfterInstantiation：实例化bean之后，相当于new这个bean之后
- postProcessPropertyValues：bean实例化后，属性注入时触发
- postProcessBeforeInitialization：初始化bean之前，相当于把bean注入spring上下文之前
- @PostConstruct直接修饰的方法
- 实现 InitializingBean 的方法 afterPropertiesSet
- bean定义时，指定的 initMethod 方法
- postProcessAfterInitialization：初始化bean之后，相当于把bean注入spring上下文之后

应用场景：

- bean生命周期的收集上报，实例化前后端的相关操作

相关博文：
