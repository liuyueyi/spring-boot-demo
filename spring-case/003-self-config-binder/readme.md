## 003-self-config-binder

自定义的配置加载，并支持动态刷新

- 基于Binder来实现，将配置注入拥有对应注解的配置类上
- 基于ApplicationContextInitializer来实现，注入自定义的配置源，然后适用于所有的@Value、配置使用的场景
  - 支持@value注解对应的自定义配置动态刷新


相关博文：

* [【基础系列】 实现一个自定义配置加载器（应用篇） | 一灰灰Blog](https://spring.hhui.top/spring-blog/2020/05/07/200507-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E8%87%AA%E5%AE%9A%E4%B9%89%E9%85%8D%E7%BD%AE%E5%8A%A0%E8%BD%BD%E5%99%A8/)
* [【基础系列】 ConfigurationProperties配置绑定中那些你不知道的事情 | 一灰灰Blog](https://spring.hhui.top/spring-blog/2021/01/17/210117-SpringBoot%E7%B3%BB%E5%88%97ConfigurationProperties%E9%85%8D%E7%BD%AE%E7%BB%91%E5%AE%9A%E4%B8%AD%E9%82%A3%E4%BA%9B%E4%BD%A0%E4%B8%8D%E7%9F%A5%E9%81%93%E7%9A%84%E4%BA%8B%E6%83%85/)
* [【基础系列】 SpringBoot应用篇@Value注解支持配置自动刷新能力扩展 | 一灰灰Blog](https://spring.hhui.top/spring-blog/2021/08/01/210801-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87-Value%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81%E9%85%8D%E7%BD%AE%E8%87%AA%E5%8A%A8%E5%88%B7%E6%96%B0%E8%83%BD%E5%8A%9B%E6%89%A9%E5%B1%95/)
* [【基础系列】 编程式属性绑定Binder | 一灰灰Blog](https://spring.hhui.top/spring-blog/2023/06/18/230618-SpringBoot%E4%B9%8B%E7%BC%96%E7%A8%8B%E5%BC%8F%E5%B1%9E%E6%80%A7%E7%BB%91%E5%AE%9ABinder/)
* [【基础系列】自定义属性配置绑定极简实现姿势介绍 | 一灰灰Blog](http://spring.hhui.top/spring-blog/2023/06/27/230627-SpringBoot%E5%9F%BA%E7%A1%80%E7%B3%BB%E5%88%97%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E5%B1%9E%E6%80%A7%E9%85%8D%E7%BD%AE%E7%BB%91%E5%AE%9A%E5%AE%9E%E7%8E%B0%E4%BB%8B%E7%BB%8D/)