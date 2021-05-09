# 150-i18n

国际化实例demo

**关键点**

### 配置信息无法获取

- 配置信息: `spring.messages.basename` 对应的value为目录 + 语言的前缀
    - 如我的配置文件为 `i18n/messages/messages_en_US.properties`， 那么这个value就应该是 `i18n/messages/messages`
    
### 中文乱码问题

- 注意确保 properties 文件的编码个是为 utf-8；
- 设置编码 `spring.messages.encoding=utf-8`

### 根据请求支持国际化

需要添加本地化的拦截器`LocaleChangeInterceptor`，来实现根据请求参数，解析语言环境

其次需要注册`LocaleResolver`，比如demo中使用`CookieLocaleResolver`，来保存国际化信息 （如果不设置它会抛异常）


### 系列博文

- [201509 SpringBoot 国际化支持实例开发](https://spring.hhui.top/spring-blog/2021/05/09/210509-SpringBoot%E5%AE%9E%E7%8E%B0%E5%9B%BD%E9%99%85%E5%8C%96%E6%94%AF%E6%8C%81%E5%AE%9E%E4%BE%8B%E5%BC%80%E5%8F%91/)
