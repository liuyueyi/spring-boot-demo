## 100-application-context-extention

上下文刷新前扩展点ApplicationContextInitializer，如实现在上下文初始化前指定激活的配置文件

核心知识点：

- 三种注册方式
  - SpringBoot启动时注册 （优先级最低）
  - SPI注册 （其次）
  - 配置文件注册 （优先级最高）
- 加载顺序
  - 相同注册方式，可以根据 `@Order` 注解来指定加载顺序

相关博文：

* [【扩展点】 容器刷新前回调ApplicationContextInitializer | 一灰灰Blog](https://spring.hhui.top/spring-blog/2022/09/27/220927-Spring%E6%89%A9%E5%B1%95%E4%B9%8B%E5%AE%B9%E5%99%A8%E5%88%B7%E6%96%B0%E5%89%8D%E5%9B%9E%E8%B0%83ApplicationContextInitializer/)
