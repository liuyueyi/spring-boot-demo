# Spring-Boot-Demo

[![Build Status](https://travis-ci.org/liuyueyi/spring-boot-demo.svg?branch=master)](https://travis-ci.org/liuyueyi/spring-boot-demo)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/liuyueyi/spring-boot-demo.svg)](http://isitmaintained.com/project/liuyueyi/spring-boot-demo "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/liuyueyi/spring-boot-demo.svg)](http://isitmaintained.com/project/liuyueyi/spring-boot-demo "Percentage of issues still open")

SpringBoot + SpringCloud + SpringSecurity学习过程中的源码汇总，沉淀记录下学习历程

## 1. 知识点图谱

所有博文集中发布在个人博客网站 ： [一灰灰Blog-Spring](http://spring.hhui.top/)

大致规划的内容包括以下章节，希望能用<del>半年到一年(严重超期)</del>的时间完成....

### I. [基础篇](http://spring.hhui.top/spring-blog/categories/SpringBoot/基础篇/)

- [x] [配置相关](http://spring.hhui.top/spring-blog/tags/Config/)
- [x] [Bean相关](http://spring.hhui.top/spring-blog/tags/Bean/)
- [x] [日志相关](http://spring.hhui.top//spring-blog/tags/Log/)
- [x] [AOP相关](http://spring.hhui.top//spring-blog/tags/AOP/)
- [ ] SPEL
- [ ] 事件通知机制

### II. 高级篇

- [ ] [db读写](http://spring.hhui.top/spring-blog/tags/DB/)
    - [x] 基本配置，数据源等
    - [x] [jdbcTemplate](http://spring.hhui.top/spring-blog/tags/JdbcTemplate/)
    - [x] [jpa](http://spring.hhui.top/spring-blog/tags/JPA/)
        - 项目工程： [spring-boot/102-jpa](spring-boot/102-jpa)
    - [x] mybatis
        - 项目工程:  [spring-boot/103-mybatis-xml](spring-boot/103-mybatis-xml) , [spring-boot/104-mybatis-noxml](spring-boot/104-mybatis-noxml)
    - [x] mybatis plus
        - 项目工程: [spring-boot/105-mybatis-plus](spring-boot/105-mybatis-plus)
    - [ ] jooq
- [ ] influxdb 时序数据库
        - 项目工程: [spring-boot/130-influxdb](spring-boot/130-influxdb) ,  [spring-boot/131-influxdb-java](spring-boot/131-influxdb-java) 
- [ ] [Mongo](http://spring.hhui.top/spring-blog/tags/Mongo/)
    - [x] 项目工程
        - 基础环境 [spring-boot/110-mongo-basic](spring-boot/110-mongo-basic)
        - mongoTemplate使用姿势 [spring-boot/111-mongo-template](spring-boot/111-mongo-template)
- [x] [Redis读写](http://spring.hhui.top/spring-blog/tags/Redis/)
    - [x] 项目工程：
        - 基本环境构建 [spring-boot/120-redis-config](spring-boot/120-redis-config)
        - jedis环境构建  [spring-boot/121-redis-jedis-config](spring-boot/121-redis-jedis-config)
        - redisTemplate使用姿势 [spring-boot/122-redis-template](spring-boot/122-redis-template)
        - lettuce环境构建 [spring-boot/123-redis-lettuce-config](spring-boot/123-redis-lettuce-config)
        - redis集群实例工程 [spring-boot/124-redis-cluster](spring-boot/124-redis-cluster)
        - 排行榜应用实例工程 [spring-case/120-redis-ranklist](spring-case/120-redis-ranklist)
        - 站点统计应用实例工程 [spring-case/124-redis-sitecount](spring-case/124-redis-sitecount)
- [ ] MemCache
- [ ] SpringCache
- [ ] 定时器 
- [ ] 搜索 ES
- [x] 搜索 [Solr](http://spring.hhui.top/spring-blog/tags/Solr/)
    - [x] 项目工程：[spring-boot/140-search-solr](spring-boot/140-search-solr)
    - [x] [基本环境搭建](http://spring.hhui.top/spring-blog/2019/05/10/190510-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87%E6%90%9C%E7%B4%A2%E4%B9%8BSolr%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA%E4%B8%8E%E7%AE%80%E5%8D%95%E6%B5%8B%E8%AF%95/)
    - [x] [新增与修改使用说明](http://spring.hhui.top/spring-blog/2019/05/26/190526-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87%E6%90%9C%E7%B4%A2Solr%E4%B9%8B%E6%96%87%E6%A1%A3%E6%96%B0%E5%A2%9E%E4%B8%8E%E4%BF%AE%E6%94%B9%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/) 
    
### III. MVC篇

- [x] 过滤器
    - [x] 项目工程: 
        - 基本使用姿势：[spring-boot/210-web-filter](spring-boot/210-web-filter)
        - filter优先级: [spring-boot/210-web-filter-order](spring-boot/210-web-filter-order)
- [ ] 拦截器
- [x] Get/Post/Put/Delete等http方法支持
- [x] 参数绑定(get/post参数解析，自定义参数解析器)
    - [x] 项目工程: [spring-boot/202-web-params](spring-boot/202-web-params)
    - [x] [请求参数解析姿势大全](http://spring.hhui.top/spring-blog/tags/%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0/)
- [x] 返回相关
    - [x] 数据返回
        - 项目:[spring-boot/207-web-response](spring-boot/207-web-response)
        - [返回数据姿势大全](http://spring.hhui.top/spring-blog/tags/%E8%BF%94%E5%9B%9E%E6%95%B0%E6%8D%AE/)
    - [x] 视图绑定, 
        - 项目: [spring-boot/204-web-freemaker](spring-boot/204-web-freemaker) | [spring-boot/204-web-thymeleaf](spring-boot/205-web-thymeleaf) [spring-boot/204-web-beetl](spring-boot/206-web-beetl)
        - [spring & 模板引擎构建web项目](http://spring.hhui.top/spring-blog/tags/%E6%A8%A1%E6%9D%BF%E5%BC%95%E6%93%8E/)
    - 返回头
- [x] 异常处理
- [ ] 安全相关(SQL/XSS等注入)
- [ ] 跨域处理
- [ ] WebSocket
    - [x] [websocket基础](http://spring.hhui.top/spring-blog/tags/WebSocket/)
- [ ] reactive


### IV. SpringCloud篇

- [ ] 注册中心
- [ ] 配置中心
- [ ] 网关路由
- [ ] 负载均衡
- [ ] 熔断器
- [ ] 链路监控
- [ ] 安全模块
- [ ] oauth
- [ ] admin

### V. 源码篇

- [ ] xxx

### VI. 项目说明

| 项目 | 说明 | 知识点 | 
| --- | --- | --- |
| **SpringBoot** | SpringBoot项目 | - |
| [000-properties](spring-boot/000-properties) | 【配置】使用姿势 | `@PropertySource` 指定配置文件，<br/> `@ConfigurationProperties` 指定配置前缀, <br/>`@value` 配置绑定|
| [001-properties](spring-boot/001-properties) | 【配置】环境选择 | 配置`spring.profiles.active`指定环境 | 
| [002-properties](spring-boot/002-properties) | 【配置】刷新示例 | SpringCloud生态配置刷新<br/>`@RefreshScope`，`EnvironmentChangeEvent`配置变更事件|
| [003-log](spring-boot/003-log) | 【日志】集成logback | logback日志集成与配置 |
| [003-log4j2](spring-boot/003-log4j2) | 【日志】集成log4j2 | log4j2日志集成与配置 |
| [004-bean](spring-boot/004-bean) | 【bean】使用姿势 | bean三种定义姿势 <br/> bean三种注入方式 |
| [005-autoconfig](spring-boot/005-autoconfig) | 【bean】自动加载 | `@Configuration`自动加载配置类 |
| [005-config-selector](spring-boot/005-config-selector) | 【bean】选择注入 | `ImportSelector` 选择在接口的多个实现中，具体实例化哪个 |
| [006-dynamicbean](spring-boot/006-dynamicbean) | 【bean】动态注册bean | `BeanDefinitionRegistryPostProcessor` 扩展实现bean动态注册 |
| [007-conditionbean](spring-boot/007-conditionbean) | 【bean】条件注入 | `@Coinditional` 使用姿势 |
| [008-beanorder](spring-boot/008-beanorder)<br/>[008-beanorder-addition](spring-boot/008-beanorder-addition)<br/> [008-beanorder-addition2](spring-boot/008-beanorder-addition2)| 【bean】加载顺序 | bean加载顺序的反面示例与正确写法 |
| [009-schedule](spring-boot/009-schedule) | 【定时器】定时任务/计划任务 | `@Scheduled` 基本语法与自定义线程池 |
| [010-aop](spring-boot/010-aop) | 【AOP】切面 | aop基本使用姿势与注意事项 |
| [011-aop-logaspect](spring-boot/011-aop-logaspect) | 【AOP】切面 | 实战，日志切面|
| [012-context-listener](spring-boot/012-context-listener) | 【Listener】事件 | ContextListener |
| [100-mysql](spring-boot/100-mysql) | 【DB】mysql整合 | - |
| [101-jdbctemplate](spring-boot/101-jdbctemplate) | 【DB】jdbctemplate使用姿势 CURD详解 | `JdbcTemplate` |
| [101-jdbctemplate-transaction](spring-boot/101-jdbctemplate-transaction) | 【DB】事务 |`@Transactional`声明式; 编程式事务 <br/> - 隔离级别 <br/> 传递属性 |
| [102-jpa](spring-boot/102-jpa) | 【DB】 jpa使用姿势 | JPA |
| [102-jpa-errorcase](spring-boot/102-jpa-errorcase) | 【DB】环境配置易错点 | `@EnableJpaRepositories`, `@EntityScan`指定扫描包 |
| [102-jpa-errorcase2](spring-boot/102-jpa-errorcase2) | 【DB】Entity映射错误 | Field映射POJO |
| [103-mybatis-xml](spring-boot/103-mybatis-xml) | 【DB】mybatis xml配置整合方式 | mybatis |
| [104-mybatis-noxml](spring-boot/104-mybatis-noxml) | 【DB】mybatis 注解整合方式 | mybatis |
| [105-mybatis-plus](spring-boot/105-mybatis-plus) | 【DB】mybatis-plus整合 | mybatis-plus |
| [110-mongo-basic](spring-boot/110-mongo-basic) | 【DB】mongodb整合 | mongodb |
| [111-mongo-template](spring-boot/111-mongo-template) | 【DB】mongodb CURD使用姿势 | `MongoTemplate` |
| [120-redis-config](spring-boot/120-redis-config)  | 【redis】环境配置与基本使用 | redis |
| [121-redis-jedis-config](spring-boot/121-redis-jedis-config) |【redis】jedis配置 | jedis |
| [122-redis-template](spring-boot/122-redis-template) | 【redis】RedisTemplate 使用姿势详解 | `RedisTemplate` |
| [123-redis-lettuce-config](spring-boot/123-redis-lettuce-config) | 【redis】lettuce配置 | lettuce |
| [124-redis-cluster](spring-boot/124-redis-cluster) | 【redis】集群使用姿势 | redis集群实例 |
| [130-influxdb](spring-boot/130-influxdb) | 【DB】influxdb整合及CURD | 时序数据库 influxdb |
| [131-influxdb-java](spring-boot/131-influxdb-java) | 【DB】influxdb封装 | 封装更服务SpringBoot规范的`InfluxTemplate`，待实现 |
| [140-search-solr](spring-boot/140-search-solr) | 【Solr】solr环境+CURD使用姿势 | `SolrTemplate`, `SolrClient` |
| [141-search-solr-auth](spring-boot/141-search-solr-auth) | 【Solr】solr开启授权无法更新索引的四种解决方案 | 解决solr更新索引报错问题 |
| [200-webflux](spring-boot/200-webflux) | 【web】WebFlux实例 | `React` |
| [201-web](spring-boot/201-web) | 【web】basic http实例 | `springmvc` |
| [202-web-params](spring-boot/202-web-params) | 【web】请求参数解析的各种姿势 | get参数解析<br/>post参数解析<br/>自定义参数解析`HandlerMethodArgumentResolver` |
| [203-websocket](spring-boot/203-websocket) | 【web】websocket实例 | `WebSocketHandler`, `WebSocketConfigurer` |
| [204-web-freemaker](spring-boot/204-web-freemaker) | 【web】freemaker引擎整合 | freemaker |
| [205-web-thymeleaf](spring-boot/205-web-thymeleaf) | 【web】thymeleaf引擎整合 | thymeleaf |
| [206-web-beetl](spring-boot/206-web-beetl) | 【web】beetl引擎整合 | beetl |
| [207-web-response](spring-boot/207-web-response) | 【web】http响应的各种姿势 | 基本数据返回 <br/> 重定向 <br/> 错误页面配置 <br/> 定制http code |
| [208-web-mapping](spring-boot/208-web-mapping) | 【web】自定义url映射规则 | `RequestCondition` |
| [209-web-error](spring-boot/209-web-error) | 【web】全局异常处理 | `ControllerAdvice`, `ExceptionHandler` |
| [210-web-filter](spring-boot/210-web-filter) | 【web】filter使用姿势 | HttpFilter过滤器 |
| [210-web-filter-order](spring-boot/210-web-filter-order) | 【web】filter优先级使用姿势 | `HttpFilter`, `@Order` |
| [211-web-servlet](spring-boot/211-web-servlet) | 【web】servlet使用姿势 | Servlet |
| [212-web-listener](spring-boot/212-web-listener) | 【web】listener知识点 | Listener |
| [219-web-asyn](spring-boot/219-web-asyn) | 【web】异步请求 |`AsyncContext方式` <br/> `Callable` <br/> `WebAsyncTask` <br/>`DeferredResult` |
| [220-web-sse](spring-boot/220-web-sse) | 【web】sse 服务器发送事件 |`SseEmitter` |
| [300-rabbitmq](spring-boot/300-rabbitmq) | 【web】rabbitmq整合 | rabbitmq |
| [301-rabbitmq-publish](spring-boot/301-rabbitmq-publish) | 【web】rabbitmq发送消息 | `RabbitTemplate` <br/> 消息确认模式 <br/> 事务模式 |
| [302-rabbitmq-consumer](spring-boot/302-rabbitmq-consumer) |【web】rabbitmq消费消息姿势 | `@RabbitListener` |
|  | | |
| **SpringCase** | 实战/应用演练项目 | - |
| [000-spi-factorybean](spring-case/000-spi-factorybean) | 借助FactoryBean实现SPI效果 | `FactoryBean` |
| [006-importbean](spring-case/006-importbean) | 将非spring项目导入Spring生态 <br/> 自定义注入实例 | `ImportBeanDefinitionRegistrar` |
| [008-bean-order](spring-case/008-bean-order)<br/>[008-bean-order-client](spring-case/008-bean-order-client) | 指定bean加载优先级，让中间件的核心bean优于业务bean被加载 |`InstantiationAwareBeanPostProcessorAdapter`, `@Import` |
| [120-redis-ranklist](spring-case/120-redis-ranklist) | redis实现排行榜 | `zset` |
| [124-redis-sitecount](spring-case/124-redis-sitecount) | redis实现站点统计 | `redisTemplate` |
| [201-web-api-version](spring-case/201-web-api-version) | web版本控制 | `RequestMappingHandlerMapping` |
| [202-web-qrcode-login](spring-case/202-web-qrcode-login) | web扫码登录实战 | `SseEmitter` |
| [203-web-rest-adapter](spring-case/203-web-rest-adapter) | url匹配规则自定义，子类继承父类的参数注解 | `RequestMappingHandlerMapping`, `HandlerMethodArgumentResolver` |
| | | |
| **SpringSecurity** | 安全 | |
| [000-basic-demo](spring-security/000-basic-demo) | 整合实例 | |
| [001-authentication-mem-config](spring-security/001-authentication-mem-config) | 内存认证 | |
| [001-authentication-mem-userdetail](spring-security/001-authentication-mem-userdetail) | 内存认证 | |
| [002-authentication-db](spring-security/002-authentication-db) | db认证 | |
| [010-accesscontrol-rbac](spring-security/010-accesscontrol-rbac) | rbac 权限管理 | |
| [011-accesscontrol-acl](spring-security/011-accesscontrol-acl) | acl权限管理 | |
| | | |
| **spring-cloud** | 微服务 | |
| [config-server](spring-cloud/config-server) | 配置中心 | |
| [eurka-server](spring-cloud/eurka-server) | 注册中心 | |
| [eurka-service-consumer](spring-cloud/eurka-service-consumer) | 服务提供者 | |
| [eurka-service-provider](spring-cloud/eurka-service-provider) | 服务消费者 | |


## 2. 系列博文

### 0. 实战系列

1. [【SpringBoot实战】Bean之注销与动态注册实现服务mock](http://spring.hhui.top/spring-blog/2018/10/17/181017-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87Bean%E4%B9%8B%E6%B3%A8%E9%94%80%E4%B8%8E%E5%8A%A8%E6%80%81%E6%B3%A8%E5%86%8C%E5%AE%9E%E7%8E%B0%E6%9C%8D%E5%8A%A1mock/)
2. [【SpringBoot实战】FactoryBean及代理实现SPI机制的实例](http://spring.hhui.top/spring-blog/2018/10/24/181024-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8BFactoryBean%E5%8F%8A%E4%BB%A3%E7%90%86%E5%AE%9E%E7%8E%B0SPI%E6%9C%BA%E5%88%B6%E7%9A%84%E5%AE%9E%E4%BE%8B/)
3. [【SpringBoot实战】借助Redis实现排行榜功能](http://spring.hhui.top/spring-blog/2018/12/25/181225-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E5%AE%9E%E7%8E%B0%E6%8E%92%E8%A1%8C%E6%A6%9C%E5%8A%9F%E8%83%BD/)
4. [【SpringBoot实战】借助Redis搭建一个简单站点统计服务](http://spring.hhui.top/spring-blog/2019/05/13/190513-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E6%90%AD%E5%BB%BA%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%AB%99%E7%82%B9%E7%BB%9F%E8%AE%A1%E6%9C%8D%E5%8A%A1/)
5. [【SpringBoot实战】AOP实现日志功能](http://spring.hhui.top/spring-blog/2019/03/13/190313-SpringCloud%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8BAOP%E5%AE%9E%E7%8E%B0%E6%97%A5%E5%BF%97%E5%8A%9F%E8%83%BD/)
6. [【SpringBoot实战】徒手撸一个扫码登录示例工程](http://spring.hhui.top/spring-blog/2020/04/02/200402-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E5%BE%92%E6%89%8B%E6%92%B8%E4%B8%80%E4%B8%AA%E6%89%AB%E7%A0%81%E7%99%BB%E5%BD%95%E7%A4%BA%E4%BE%8B%E5%B7%A5%E7%A8%8B/)


### 1. 基础系列

基础系列博文包括 AOP, IoC(DI,Bean), 日志, 自动配置等

**配置**

- [【基础系列】SpringBoot基础篇配置信息之如何读取配置信息](http://spring.hhui.top/spring-blog/2018/09/19/180919-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E5%A6%82%E4%BD%95%E8%AF%BB%E5%8F%96%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF/)
- [【基础系列】SpringBoot基础篇配置信息之多环境配置信息](http://spring.hhui.top/spring-blog/2018/09/20/180920-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E5%A4%9A%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF/)
- [【基础系列】SpringBoot基础篇配置信息之自定义配置指定与配置内引用](http://spring.hhui.top/spring-blog/2018/09/21/180921-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E9%85%8D%E7%BD%AE%E6%8C%87%E5%AE%9A%E4%B8%8E%E9%85%8D%E7%BD%AE%E5%86%85%E5%BC%95%E7%94%A8/)
- [【基础系列】SpringBoot配置信息之配置刷新](http://spring.hhui.top/spring-blog/2018/09/22/180922-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E9%85%8D%E7%BD%AE%E5%88%B7%E6%96%B0/)
- [【基础系列】SpringBoot配置信息之默认配置](http://spring.hhui.top/spring-blog/2018/09/25/180925-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE/)


**IoC(DI/bean)**

-  [【基础系列】Bean之基本定义与使用](http://spring.hhui.top/spring-blog/2018/10/09/181009-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E5%9F%BA%E6%9C%AC%E5%AE%9A%E4%B9%89%E4%B8%8E%E4%BD%BF%E7%94%A8/)
-  [【基础系列】Bean之自动加载](http://spring.hhui.top/spring-blog/2018/10/12/181012-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E8%87%AA%E5%8A%A8%E5%8A%A0%E8%BD%BD/)
-  [【基础系列】Bean之条件注入@Condition使用姿势](http://spring.hhui.top/spring-blog/2018/10/18/181018-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E6%9D%A1%E4%BB%B6%E6%B3%A8%E5%85%A5-Condition%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【基础系列】Bean之@ConditionalOnBean与@ConditionalOnClass](http://spring.hhui.top/spring-blog/2018/10/19/181019-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B-ConditionalOnBean%E4%B8%8E@ConditionalOnClass/)
-  [【基础系列】Bean之条件注入@ConditionalOnProperty](http://spring.hhui.top/spring-blog/2018/10/19/181019-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E6%9D%A1%E4%BB%B6%E6%B3%A8%E5%85%A5-ConditionalOnProperty/)
-  [【基础系列】Bean之条件注入@ConditionalOnExpression](http://spring.hhui.top/spring-blog/2018/10/19/181019-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E6%9D%A1%E4%BB%B6%E6%B3%A8%E5%85%A5-ConditionalOnExpression/)
-  [【基础系列】Bean之多实例选择](http://spring.hhui.top/spring-blog/2018/10/22/181022-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E5%A4%9A%E5%AE%9E%E4%BE%8B%E9%80%89%E6%8B%A9/)
-  [【配置系列】Bean加载顺序之错误使用姿势辟谣](http://spring.hhui.top/spring-blog/2019/10/23/191023-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BBean%E5%8A%A0%E8%BD%BD%E9%A1%BA%E5%BA%8F%E4%B9%8B%E9%94%99%E8%AF%AF%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%BE%9F%E8%B0%A3/)
-  [【基础系列】指定Bean初始化顺序的若干姿势](http://spring.hhui.top/spring-blog/2019/10/29/191029-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BBean%E4%B9%8B%E6%8C%87%E5%AE%9A%E5%88%9D%E5%A7%8B%E5%8C%96%E9%A1%BA%E5%BA%8F%E7%9A%84%E8%8B%A5%E5%B9%B2%E5%A7%BF%E5%8A%BF/)
-  [【基础系列】自动配置选择生效](http://spring.hhui.top/spring-blog/2019/12/14/191214-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E8%87%AA%E5%8A%A8%E9%85%8D%E7%BD%AE%E9%80%89%E6%8B%A9%E7%94%9F%E6%95%88/)
-  [【基础系列】Bean之动态注册](http://spring.hhui.top/spring-blog/2018/10/13/181013-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E5%8A%A8%E6%80%81%E6%B3%A8%E5%86%8C/)
-  [【基础系列】Bean之注销与动态注册实现服务mock（应用篇）](http://spring.hhui.top/spring-blog/2018/10/17/181017-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87Bean%E4%B9%8B%E6%B3%A8%E9%94%80%E4%B8%8E%E5%8A%A8%E6%80%81%E6%B3%A8%E5%86%8C%E5%AE%9E%E7%8E%B0%E6%9C%8D%E5%8A%A1mock/)
-  [【基础系列】FactoryBean及代理实现SPI机制的实例（应用篇）](http://spring.hhui.top/spring-blog/2018/10/24/181024-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8BFactoryBean%E5%8F%8A%E4%BB%A3%E7%90%86%E5%AE%9E%E7%8E%B0SPI%E6%9C%BA%E5%88%B6%E7%9A%84%E5%AE%9E%E4%BE%8B/)
-  [【基础系列】从0到1实现一个自定义Bean注册器（应用篇）](http://spring.hhui.top/spring-blog/2019/12/13/191213-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E4%BB%8E0%E5%88%B01%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E8%87%AA%E5%AE%9A%E4%B9%89Bean%E6%B3%A8%E5%86%8C%E5%99%A8/)
-  [【基础系列-实战】如何指定bean最先加载(应用篇)](http://spring.hhui.top/spring-blog/2020/03/17/200317-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E5%AE%9E%E6%88%98%EF%BC%9A%E5%A6%82%E4%BD%95%E6%8C%87%E5%AE%9A%E7%89%B9%E5%AE%9Abean%E6%9C%80%E5%85%88%E5%8A%A0%E8%BD%BD/)

**AOP相关**

- [【基础系列】AOP之基本使用姿势小结](http://spring.hhui.top/spring-blog/2019/03/01/190301-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87AOP%E4%B9%8B%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E5%B0%8F%E7%BB%93/)
- [【基础系列】AOP之高级使用技能](http://spring.hhui.top/spring-blog/2019/03/02/190302-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87AOP%E4%B9%8B%E9%AB%98%E7%BA%A7%E4%BD%BF%E7%94%A8%E6%8A%80%E8%83%BD/)
- [【基础系列】AOP之拦截优先级详解](http://spring.hhui.top/spring-blog/2019/03/10/190310-SpringCloud%E5%9F%BA%E7%A1%80%E7%AF%87AOP%E4%B9%8B%E6%8B%A6%E6%88%AA%E4%BC%98%E5%85%88%E7%BA%A7%E8%AF%A6%E8%A7%A3/)
- [【基础系列】AOP实现一个日志插件（应用篇）](http://spring.hhui.top/spring-blog/2019/03/13/190313-SpringCloud%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8BAOP%E5%AE%9E%E7%8E%B0%E6%97%A5%E5%BF%97%E5%8A%9F%E8%83%BD/)

**日志**

- [【基础系列】日志管理之默认配置](http://spring.hhui.top/spring-blog/2018/09/27/180927-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E6%97%A5%E5%BF%97%E7%AE%A1%E7%90%86%E4%B9%8B%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE/)
- [【基础系列】日志管理之logback配置文件](http://spring.hhui.top/spring-blog/2018/09/29/180929-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E6%97%A5%E5%BF%97%E7%AE%A1%E7%90%86%E4%B9%8Blogback%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6/)


### 2. DB系列

我们将db区分为传统的关系型数据库如mysql，NoSql如redis、mongodb，时序数据库influxdb

**MongoDB**


- [【DB系列】MongoDB之基本环境搭建与使用](http://spring.hhui.top/spring-blog/2018/12/13/181213-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87MongoDB%E4%B9%8B%E5%9F%BA%E6%9C%AC%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA%E4%B8%8E%E4%BD%BF%E7%94%A8/)
- [【DB系列】MongoDB之查询基本使用姿势](http://spring.hhui.top/spring-blog/2019/01/13/190113-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87MongoDB%E4%B9%8B%E6%9F%A5%E8%AF%A2%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [【DB系列】MongoDB之如何新增文档](http://spring.hhui.top/spring-blog/2019/01/24/190124-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87MongoDB%E4%B9%8B%E5%A6%82%E4%BD%95%E6%96%B0%E5%A2%9E%E6%96%87%E6%A1%A3/)
- [【DB系列】MongoDB之修改基本使用姿势](http://spring.hhui.top/spring-blog/2019/02/18/190218-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87MongoDB%E4%B9%8B%E4%BF%AE%E6%94%B9%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)

**redis**

-  [【DB系列】Redis之基本配置](http://spring.hhui.top/spring-blog/2018/10/29/181029-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8B%E5%9F%BA%E6%9C%AC%E9%85%8D%E7%BD%AE/)
-  [【DB系列】Redis之Jedis配置](http://spring.hhui.top/spring-blog/2018/11/01/181101-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BJedis%E9%85%8D%E7%BD%AE/)
-  [【DB系列】Redis之String数据结构的读写](http://spring.hhui.top/spring-blog/2018/11/08/181108-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BString%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E7%9A%84%E8%AF%BB%E5%86%99/)
-  [【DB系列】Redis之List数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/11/09/181109-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BList%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【DB系列】Redis之Hash数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/02/181202-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BHash%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【DB系列】Redis之Set数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/11/181211-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BSet%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【DB系列】Redis之ZSet数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/12/181212-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BZSet%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【DB系列】Redis集群环境配置](http://spring.hhui.top/spring-blog/2019/09/27/190927-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BRedis%E9%9B%86%E7%BE%A4%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE/)
-  [【DB系列】借助Redis实现排行榜功能（应用篇）](http://spring.hhui.top/spring-blog/2018/12/25/181225-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E5%AE%9E%E7%8E%B0%E6%8E%92%E8%A1%8C%E6%A6%9C%E5%8A%9F%E8%83%BD/)
-  [【DB系列】借助Redis搭建一个简单站点统计服务（应用篇）](http://spring.hhui.top/spring-blog/2019/05/13/190513-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E6%90%AD%E5%BB%BA%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%AB%99%E7%82%B9%E7%BB%9F%E8%AE%A1%E6%9C%8D%E5%8A%A1/)

**mysql**

分别介绍多种不同的操作姿势

-  [【DB系列】mysql基本项目演示](http://spring.hhui.top/spring-blog/2018/09/26/180926-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87DB%E4%B9%8B%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8/)
-  [【DB系列】JdbcTemplate之数据插入使用姿势详解](http://spring.hhui.top/spring-blog/2019/04/07/190407-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87JdbcTemplate%E4%B9%8B%E6%95%B0%E6%8D%AE%E6%8F%92%E5%85%A5%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%AF%A6%E8%A7%A3/)
-  [【DB系列】JdbcTemplate之数据查询上篇](http://spring.hhui.top/spring-blog/2019/04/12/190412-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87JdbcTemplate%E4%B9%8B%E6%95%B0%E6%8D%AE%E6%9F%A5%E8%AF%A2%E4%B8%8A%E7%AF%87/)
-  [【DB系列】JdbcTemplate之数据查询下篇](http://spring.hhui.top/spring-blog/2019/04/17/190417-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87JdbcTemplate%E4%B9%8B%E6%95%B0%E6%8D%AE%E6%9F%A5%E8%AF%A2%E4%B8%8B%E7%AF%87/)
-  [【DB系列】JdbcTemplate之数据更新与删除](http://spring.hhui.top/spring-blog/2019/04/18/190418-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87JdbcTemplate%E4%B9%8B%E6%95%B0%E6%8D%AE%E6%9B%B4%E6%96%B0%E4%B8%8E%E5%88%A0%E9%99%A4/)
-  [【DB系列】JPA之基础环境搭建](http://spring.hhui.top/spring-blog/2019/06/12/190612-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E5%9F%BA%E7%A1%80%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
-  [【DB系列】JPA之新增记录使用姿势](http://spring.hhui.top/spring-blog/2019/06/14/190614-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E6%96%B0%E5%A2%9E%E8%AE%B0%E5%BD%95%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【DB系列】JPA之update使用姿势](http://spring.hhui.top/spring-blog/2019/06/23/190623-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bupdate%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【DB系列】JPA之delete使用姿势详解](http://spring.hhui.top/spring-blog/2019/07/02/190702-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bdelete%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%AF%A6%E8%A7%A3/)
-  [【DB系列】JPA之query使用姿势详解之基础篇](http://spring.hhui.top/spring-blog/2019/07/17/190717-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bquery%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%AF%A6%E8%A7%A3%E4%B9%8B%E5%9F%BA%E7%A1%80%E7%AF%87/)
-  [【DB系列】JPA之指定id保存](http://spring.hhui.top/spring-blog/2019/11/19/191119-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E6%8C%87%E5%AE%9Aid%E4%BF%9D%E5%AD%98/)
-  [【DB系列】JPA 错误姿势之环境配置问题](http://spring.hhui.top/spring-blog/2019/12/18/191218-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA-%E9%94%99%E8%AF%AF%E5%A7%BF%E5%8A%BF%E4%B9%8B%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE%E9%97%AE%E9%A2%98/)
-  [【DB系列】JPA错误姿势之Entity映射](http://spring.hhui.top/spring-blog/2020/01/03/200103-SpringBoot%E7%B3%BB%E5%88%97JPA%E9%94%99%E8%AF%AF%E5%A7%BF%E5%8A%BF%E4%B9%8BEntity%E6%98%A0%E5%B0%84/)
-  [【DB系列】Mybatis+xml整合篇](http://spring.hhui.top/spring-blog/2019/12/27/191227-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BMybatis-xml%E6%95%B4%E5%90%88%E7%AF%87/)
-  [【DB系列】Mybatis+注解整合篇](http://spring.hhui.top/spring-blog/2019/12/30/191230-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BMybatis-%E6%B3%A8%E8%A7%A3%E6%95%B4%E5%90%88%E7%AF%87/)
-  [【DB系列】MybatisPlus整合篇](http://spring.hhui.top/spring-blog/2019/12/31/191231-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BMybatisPlus%E6%95%B4%E5%90%88%E7%AF%87/)
-  [【DB系列】声明式事务Transactional](http://spring.hhui.top/spring-blog/2020/01/19/200119-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E5%A3%B0%E6%98%8E%E5%BC%8F%E4%BA%8B%E5%8A%A1Transactional/)
-  [【DB系列】事务隔离级别知识点小结](http://spring.hhui.top/spring-blog/2020/01/20/200120-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E4%BA%8B%E5%8A%A1%E9%9A%94%E7%A6%BB%E7%BA%A7%E5%88%AB%E7%9F%A5%E8%AF%86%E7%82%B9%E5%B0%8F%E7%BB%93/)
-  [【DB系列】事务传递属性](http://spring.hhui.top/spring-blog/2020/02/02/200202-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E4%BA%8B%E5%8A%A1%E4%BC%A0%E9%80%92%E5%B1%9E%E6%80%A7/)
-  [【DB系列】事务不生效的几种case](http://spring.hhui.top/spring-blog/2020/02/03/200203-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E4%BA%8B%E5%8A%A1%E4%B8%8D%E7%94%9F%E6%95%88%E7%9A%84%E5%87%A0%E7%A7%8Dcase/)
-  [【DB系列】编程式事务使用姿势介绍篇](http://spring.hhui.top/spring-blog/2020/02/04/200204-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E7%BC%96%E7%A8%8B%E5%BC%8F%E4%BA%8B%E5%8A%A1%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E4%BB%8B%E7%BB%8D%E7%AF%87/)
-  [【DB系列】SpringBoot+Mysql 无法保存emoj表情](http://spring.hhui.top/spring-blog/2019/12/10/191210-SpringBoot-Mysql-%E6%97%A0%E6%B3%95%E4%BF%9D%E5%AD%98emoj%E8%A1%A8%E6%83%85%EF%BC%9F/)


### 3. 搜索系列

搜索主要会区分solr和es

**solr**

-  [【搜索系列】Solr环境搭建与简单测试](http://spring.hhui.top/spring-blog/2019/05/10/190510-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87%E6%90%9C%E7%B4%A2%E4%B9%8BSolr%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA%E4%B8%8E%E7%AE%80%E5%8D%95%E6%B5%8B%E8%AF%95/)
-  [【搜索系列】Solr之文档新增与修改使用姿势](http://spring.hhui.top/spring-blog/2019/05/26/190526-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87%E6%90%9C%E7%B4%A2Solr%E4%B9%8B%E6%96%87%E6%A1%A3%E6%96%B0%E5%A2%9E%E4%B8%8E%E4%BF%AE%E6%94%B9%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【搜索系列】Solr文档删除](http://spring.hhui.top/spring-blog/2020/01/14/200114-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BSolr%E4%B9%8B%E6%96%87%E6%A1%A3%E5%88%A0%E9%99%A4/)
-  [【搜索系列】Solr查询使用姿势小结](http://spring.hhui.top/spring-blog/2020/01/15/200115-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BSolr%E4%B9%8B%E6%9F%A5%E8%AF%A2%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E5%B0%8F%E7%BB%93/)
-  [【搜索系列】Solr身份认证与授权更新异常解决方案](http://spring.hhui.top/spring-blog/2020/03/30/200330-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BSolr%E8%BA%AB%E4%BB%BD%E8%AE%A4%E8%AF%81%E4%B8%8E%E6%8E%88%E6%9D%83%E6%9B%B4%E6%96%B0%E5%BC%82%E5%B8%B8%E9%97%AE%E9%A2%98%E5%88%86%E6%9E%90/)

### 4. MQ系列

消息队列，如rabbitmq, rocketmq, activemq, kafaka

**rabbitmq**

-  [【MQ系列】springboot + rabbitmq初体验](http://spring.hhui.top/spring-blog/2020/02/10/200210-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BRabbitMq%E5%88%9D%E4%BD%93%E9%AA%8C/)
-  [【MQ系列】RabbitMq核心知识点小结](http://spring.hhui.top/spring-blog/2020/02/12/200212-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BRabbitMq%E6%A0%B8%E5%BF%83%E7%9F%A5%E8%AF%86%E7%82%B9%E5%B0%8F%E7%BB%93/)
-  [【MQ系列】SprigBoot + RabbitMq发送消息基本使用姿势](http://spring.hhui.top/spring-blog/2020/02/18/200218-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BRabbitMq%E5%8F%91%E9%80%81%E6%B6%88%E6%81%AF%E7%9A%84%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【MQ系列】RabbitMq消息确认机制/事务的使用姿势](http://spring.hhui.top/spring-blog/2020/02/19/200219-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BRabbitMq%E6%B6%88%E6%81%AF%E7%A1%AE%E8%AE%A4%E6%9C%BA%E5%88%B6-%E4%BA%8B%E5%8A%A1%E7%9A%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【MQ系列】RabbitListener消费基本使用姿势介绍](http://spring.hhui.top/spring-blog/2020/03/18/200318-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BRabbitListener%E6%B6%88%E8%B4%B9%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E4%BB%8B%E7%BB%8D/)

### 5. WEB系列

web系列的东西就比较多了，基本上日常开发中，你需要的都会有；你没用过的也会有

-  [【WEB系列】Spring MVC之基于xml配置的web应用构建](http://spring.hhui.top/spring-blog/2019/03/16/190316-Spring-MVC%E4%B9%8B%E5%9F%BA%E4%BA%8Exml%E9%85%8D%E7%BD%AE%E7%9A%84web%E5%BA%94%E7%94%A8%E6%9E%84%E5%BB%BA/)
-  [【WEB系列】Spring MVC之基于java config无xml配置的web应用构建](http://spring.hhui.top/spring-blog/2019/03/17/190317-Spring-MVC%E4%B9%8B%E5%9F%BA%E4%BA%8Ejava-config%E6%97%A0xml%E9%85%8D%E7%BD%AE%E7%9A%84web%E5%BA%94%E7%94%A8%E6%9E%84%E5%BB%BA/)
-  [【WEB系列】一个web demo应用构建全过程](http://spring.hhui.top/spring-blog/2019/03/19/190319-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87WEB%E4%B9%8Bdemo%E5%BA%94%E7%94%A8%E6%9E%84%E5%BB%BA/)
-  [【WEB系列】Spring MVC之Filter基本使用姿势](http://spring.hhui.top/spring-blog/2019/03/23/190323-Spring-MVC%E4%B9%8BFilter%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【WEB系列】过滤器Filter使用指南](http://spring.hhui.top/spring-blog/2019/10/16/191016-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%BF%87%E6%BB%A4%E5%99%A8Filter%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/)
-  [【WEB系列】过滤器Filter使用指南扩展篇](http://spring.hhui.top/spring-blog/2019/10/18/191018-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%BF%87%E6%BB%A4%E5%99%A8Filter%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97%E6%89%A9%E5%B1%95%E7%AF%87/)
-  [【WEB系列】Servlet 注册的四种姿势](http://spring.hhui.top/spring-blog/2019/11/22/191122-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87Servlet-%E6%B3%A8%E5%86%8C%E7%9A%84%E5%9B%9B%E7%A7%8D%E5%A7%BF%E5%8A%BF/)
-  [【WEB系列】Listener四种注册姿势](http://spring.hhui.top/spring-blog/2019/12/06/191206-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87Listener%E5%9B%9B%E7%A7%8D%E6%B3%A8%E5%86%8C%E5%A7%BF%E5%8A%BF/)
-  [【WEB系列】Get请求参数解析姿势汇总](http://spring.hhui.top/spring-blog/2019/08/24/190824-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BGet%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0%E8%A7%A3%E6%9E%90%E5%A7%BF%E5%8A%BF%E6%B1%87%E6%80%BB/)
-  [【WEB系列】Post请求参数解析姿势汇总](http://spring.hhui.top/spring-blog/2019/08/28/190828-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BPost%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0%E8%A7%A3%E6%9E%90%E5%A7%BF%E5%8A%BF%E6%B1%87%E6%80%BB/)
-  [【WEB系列】如何自定义参数解析器](http://spring.hhui.top/spring-blog/2019/08/31/190831-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E5%A6%82%E4%BD%95%E8%87%AA%E5%AE%9A%E4%B9%89%E5%8F%82%E6%95%B0%E8%A7%A3%E6%9E%90%E5%99%A8/)
-  [【WEB系列】自定义请求匹配条件RequestCondition](http://spring.hhui.top/spring-blog/2019/12/22/191222-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E8%AF%B7%E6%B1%82%E5%8C%B9%E9%85%8D%E6%9D%A1%E4%BB%B6RequestCondition/)
-  [【WEB系列】Freemaker环境搭建](http://spring.hhui.top/spring-blog/2019/08/16/190816-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BFreemaker%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
-  [【WEB系列】Thymeleaf环境搭建](http://spring.hhui.top/spring-blog/2019/08/20/190820-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BThymeleaf%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
-  [【WEB系列】Beetl环境搭建](http://spring.hhui.top/spring-blog/2019/08/22/190822-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BBeetl%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
-  [【WEB系列】返回文本、网页、图片的操作姿势](http://spring.hhui.top/spring-blog/2019/09/13/190913-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%BF%94%E5%9B%9E%E6%96%87%E6%9C%AC%E3%80%81%E7%BD%91%E9%A1%B5%E3%80%81%E5%9B%BE%E7%89%87%E7%9A%84%E6%93%8D%E4%BD%9C%E5%A7%BF%E5%8A%BF/)
-  [【WEB系列】请求重定向](http://spring.hhui.top/spring-blog/2019/09/29/190929-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E9%87%8D%E5%AE%9A%E5%90%91/)
-  [【WEB系列】404、500异常页面配置](http://spring.hhui.top/spring-blog/2019/09/30/190930-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B404%E3%80%81500%E5%BC%82%E5%B8%B8%E9%A1%B5%E9%9D%A2%E9%85%8D%E7%BD%AE/)
-  [【WEB系列】全局异常处理](http://spring.hhui.top/spring-blog/2019/10/10/191010-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E5%85%A8%E5%B1%80%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86/)
-  [【WEB系列】自定义异常处理HandlerExceptionResolver](http://spring.hhui.top/spring-blog/2019/10/12/191012-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86HandlerExceptionResolver/)
-  [【WEB系列】开启GZIP数据压缩](http://spring.hhui.top/spring-blog/2019/11/20/191120-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BWeb%E7%AF%87%E4%B9%8B%E5%BC%80%E5%90%AFGZIP%E6%95%B0%E6%8D%AE%E5%8E%8B%E7%BC%A9/)
-  [【WEB系列】RestTemplate 4xx/5xx 异常信息捕获](http://spring.hhui.top/spring-blog/2020/01/04/200104-SpringWeb%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BRestTemplate-4xx-5xx-%E5%BC%82%E5%B8%B8%E4%BF%A1%E6%81%AF%E6%8D%95%E8%8E%B7/)
-  [【WEB系列】自定义返回Http Code的n种姿势](http://spring.hhui.top/spring-blog/2020/01/05/200105-SpringBoot%E7%B3%BB%E5%88%97web%E7%AF%87%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E8%BF%94%E5%9B%9EHttp-Code%E7%9A%84n%E7%A7%8D%E5%A7%BF%E5%8A%BF/)
-  [【WEB系列】异步请求知识点与使用姿势小结](http://spring.hhui.top/spring-blog/2020/03/29/200329-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E5%BC%82%E6%AD%A5%E8%AF%B7%E6%B1%82%E6%9C%80%E5%85%A8%E7%9F%A5%E8%AF%86%E7%82%B9%E4%B8%8E%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
-  [【WEB系列】SSE服务器发送事件详解](http://spring.hhui.top/spring-blog/2020/04/01/200401-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BSSE%E6%9C%8D%E5%8A%A1%E5%99%A8%E5%8F%91%E9%80%81%E4%BA%8B%E4%BB%B6%E8%AF%A6%E8%A7%A3/)
-  [【WEB系列】springboot + websocket初体验](http://spring.hhui.top/spring-blog/2019/04/21/190421-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87WEB%E4%B9%8Bwebsocket%E7%9A%84%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E/)

**采坑、填坑**

-  [【WEB系列】SpringBoot文件上传异常之提示The temporary upload location xxx is not valid（填坑篇）](http://spring.hhui.top/spring-blog/2019/02/13/190213-SpringBoot%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E5%BC%82%E5%B8%B8%E4%B9%8B%E6%8F%90%E7%A4%BAThe-temporary-upload-location-xxx-is-not-valid/)
-  [【WEB系列】中文乱码问题解决（填坑篇）](http://spring.hhui.top/spring-blog/2019/09/05/190905-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E4%B8%AD%E6%96%87%E4%B9%B1%E7%A0%81%E9%97%AE%E9%A2%98%E8%A7%A3%E5%86%B3/)
-  [【WEB系列】RestTemplate之urlencode参数解析异常全程分析（填坑篇）](http://spring.hhui.top/spring-blog/2019/03/27/190327-Spring-RestTemplate%E4%B9%8Burlencode%E5%8F%82%E6%95%B0%E8%A7%A3%E6%9E%90%E5%BC%82%E5%B8%B8%E5%85%A8%E7%A8%8B%E5%88%86%E6%9E%90/)

**应用实战**

-  [【WEB系列】实现后端的接口版本支持（应用篇）](http://spring.hhui.top/spring-blog/2019/12/25/191225-SpringBoot-%E5%BA%94%E7%94%A8%E7%AF%87-%E5%AE%9E%E7%8E%B0%E5%90%8E%E7%AB%AF%E7%9A%84%E6%8E%A5%E5%8F%A3%E7%89%88%E6%9C%AC%E6%94%AF%E6%8C%81/)
-  [【WEB系列】徒手撸一个扫码登录示例工程（应用篇）](http://spring.hhui.top/spring-blog/2020/04/02/200402-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E5%BE%92%E6%89%8B%E6%92%B8%E4%B8%80%E4%B8%AA%E6%89%AB%E7%A0%81%E7%99%BB%E5%BD%95%E7%A4%BA%E4%BE%8B%E5%B7%A5%E7%A8%8B/)

### 6. SpringSecurity系列

记录SpringSecurity相关的所有技术文章，分类汇总如下，持续更新中

**简单抽象的说一下SpringSecurity它的定义**

- 很🐂的认证和访问权限校验框架

**那么具体能干嘛？**

- 用户登录认证：用户名+密码登录，确定用户身份
- 用户访问鉴权（常见的ACL访问控制列表，RBAC角色访问控制）：判定是否有权限访问某个资源
- 安全保护（CSRF跨站点攻击,Session Fixation会话固定攻击…）

**相关博文**

- [【SpringSecurity系列】起源篇（零）](http://spring.hhui.top/spring-blog/2019/12/23/191223-SpringBoot-%E6%95%B4%E5%90%88-SpringSecurity-%E4%B9%8B%E8%B5%B7%E6%BA%90%E7%AF%87%EF%BC%88%E9%9B%B6%EF%BC%89/)
- [【SpringSecurity系列】基于内存认证（一）](http://spring.hhui.top/spring-blog/2020/01/11/200111-SpringBoot-%E6%95%B4%E5%90%88-SpringSecurity-%E4%B9%8B%E5%9F%BA%E4%BA%8E%E5%86%85%E5%AD%98%E8%AE%A4%E8%AF%81%EF%BC%88%E4%B8%80%EF%BC%89/)


### 7. SpringCloud系列

微服务系列

**踩坑、填坑**

- [【Feign系列】Feign请求参数包装异常问题定位](http://spring.hhui.top/spring-blog/2019/03/06/190306-SpringCloud%E4%B9%8BFeign%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0%E5%8C%85%E8%A3%85%E5%BC%82%E5%B8%B8%E9%97%AE%E9%A2%98%E5%AE%9A%E4%BD%8D/)


## 3. 其他

拒绝单机，欢迎start或者加好友支持


### 声明

尽信书则不如，已上内容，一家之言，因个人能力有限，难免有疏漏和错误之处，如发现bug或者有更好的建议，欢迎批评指正，不吝感激

- 微博地址: 小灰灰Blog
- QQ： 一灰灰/3302797840
- WeChat: 一灰/liuyueyi25

### 扫描关注

公众号&博客

![QrCode](https://gitee.com/liuyueyi/Source/raw/master/img/info/blogInfoV2.png)


打赏码

![pay](https://gitee.com/liuyueyi/Source/raw/master/img/pay/pay.png)