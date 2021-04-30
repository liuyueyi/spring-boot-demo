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

- [200330-SpringBoot系列教程之Solr身份认证与授权更新异常问题分析](http://spring.hhui.top/spring-blog/2020/03/30/200330-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BSolr%E8%BA%AB%E4%BB%BD%E8%AE%A4%E8%AF%81%E4%B8%8E%E6%8E%88%E6%9D%83%E6%9B%B4%E6%96%B0%E5%BC%82%E5%B8%B8%E9%97%AE%E9%A2%98%E5%88%86%E6%9E%90/)
