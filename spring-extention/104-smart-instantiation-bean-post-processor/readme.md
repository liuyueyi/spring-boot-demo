## 104-smart-instantiation-bean-post-processor

bean的实例化、初始化、注入前后的扩展点，三层循环解决循环依赖，指定bean的实例化构造方法

核心知识点：

- `SmartInstantiationAwareBeanPostProcessor`

核心方法

- predictBeanType: 该触发点发生在postProcessBeforeInstantiation之前，通常适用于预测bean的类型，返回第一个预测成功的Class类型
- determineCandidateConstructors: 在postProcessBeforeInstantiation之后，在new bean()之前，返回所有的构造方法，可用于自定义选择bean对象的构造器；
    - 如果bean是直接由 @Bean + new Xxx() 方法声明时，它不会被触发
- getEarlyBeanReference：在postProcessAfterInstantiation 实力化之后，初始化之前
    - 当有循环依赖的场景，三级缓存策略中，借助它来直接返回实例化的对象（注意此时这个bean可能并没有初始化，没有完成注入之类的操作）

应用场景：

- bean有多个构造方法时，可以使用它来选择具体的实例化构造方法

相关博文：
