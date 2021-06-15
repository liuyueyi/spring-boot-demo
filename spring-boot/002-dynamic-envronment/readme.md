## 002-dynamic-envronment

### 项目说明

本项目演示了如何使用自定义的配置源，以及配置的动态刷新

- 自定义 `MapPropertySource`来实现自定义的配置源
- 通过刷新`MapPropertySource`的source，从而实现配置的动态刷新
- 注意将其注册到`Envrionment`之后，可以配合`@Value`注解来试下自定义配置源的配置绑定（注意这时候不支持刷新，如果需要配置刷新，结合 002-properties-value 这个项目来实现动态刷新支持） 

### 博文说明

- [210610-SpringBoot基础篇之自定义配置源的使用姿势](https://spring.hhui.top/spring-blog/2021/06/10/210610-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E9%85%8D%E7%BD%AE%E6%BA%90%E7%9A%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)