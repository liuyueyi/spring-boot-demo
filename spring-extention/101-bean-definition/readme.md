## 101-bean-definition

项目中的bean定义读取之后，实例化之前，用于实现自定义的bean注册加载处理

核心知识点：

- `BeanDefinitionRegistryPostProcessor`
- 自定义的bean注册

应用场景：

- 手动实现bean的注册（比如某些特殊的bean注册）
- 对已有的bean定义进行能力增强or限制；如实例中的 NormalBean，手动加上init方法

相关博文：

* [【扩展点】 自定义bean注册扩展机制BeanDefinitionRegistryPostProcessor | 一灰灰Blog](https://spring.hhui.top/spring-blog/2022/10/26/221026-Spring扩展点之自定义bean注册/)
