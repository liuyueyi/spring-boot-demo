## 100-bean-factory-post-processor

项目中的bean定义读取之后，实例化之前，可以做一些bean定义修改、自定义bean注册等操作； 

注意 `BeanDefinitionRegistryPostProcessor` 实际上也是 `BeanFactoryPostProcessor` 的一个子类

核心知识点：

- `BeanFactoryPostProcessor`

应用场景：

- 配置解析注入： PropertySourcesPlaceholderConfigurer
- 一个典型的使用case，Environment中的配置读取、解析规则

相关博文：
