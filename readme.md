# Spring-Boot-Demo

[![Build Status](https://travis-ci.org/liuyueyi/spring-boot-demo.svg?branch=master)](https://travis-ci.org/liuyueyi/spring-boot-demo)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/liuyueyi/spring-boot-demo.svg)](http://isitmaintained.com/project/liuyueyi/spring-boot-demo "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/liuyueyi/spring-boot-demo.svg)](http://isitmaintained.com/project/liuyueyi/spring-boot-demo "Percentage of issues still open")

SpringBoot与SpringCloud学习过程中的源码汇总，沉淀记录下学习历程

## 1. 知识点图谱

所有博文集中发布在个人博客网站 ： [一灰灰Blog-Spring](http://spring.hhui.top/)

大致规划的内容包括以下章节，希望能用半年到一年的时间完成....

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
    - [ ] mybatis
    - [ ] mybatis plus
    - [ ] hibernate
    - [ ] jooq
- [ ] influxdb 时序数据库
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
- [ ] 搜索 [Solr](http://spring.hhui.top/spring-blog/tags/Solr/)
    - [x] 项目工程：[spring-boot/140-search-solr](spring-boot/140-search-solr)
    - [x] [基本环境搭建](http://spring.hhui.top/spring-blog/2019/05/10/190510-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87%E6%90%9C%E7%B4%A2%E4%B9%8BSolr%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA%E4%B8%8E%E7%AE%80%E5%8D%95%E6%B5%8B%E8%AF%95/)
    - [x] [新增与修改使用说明](http://spring.hhui.top/spring-blog/2019/05/26/190526-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87%E6%90%9C%E7%B4%A2Solr%E4%B9%8B%E6%96%87%E6%A1%A3%E6%96%B0%E5%A2%9E%E4%B8%8E%E4%BF%AE%E6%94%B9%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/) 
    
### III. MVC篇

- [ ] 过滤器 & 拦截器
- [ ] Get/Post/Put/Delete等http方法支持
- [x] 参数绑定(get/post参数解析，自定义参数解析器)
    - [x] 项目工程: [spring-boot/202-web-params](spring-boot/202-web-params)
    - [x] [请求参数解析姿势大全](http://spring.hhui.top/spring-blog/tags/%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0/)
- [ ] 返回相关
    - [x] 数据返回
        - 项目:[spring-boot/207-web-response](spring-boot/207-web-response)
        - [返回数据姿势大全](http://spring.hhui.top/spring-blog/tags/%E8%BF%94%E5%9B%9E%E6%95%B0%E6%8D%AE/)
    - [x] 视图绑定, 
        - 项目: [spring-boot/204-web-freemaker](spring-boot/204-web-freemaker) | [spring-boot/204-web-thymeleaf](spring-boot/205-web-thymeleaf) [spring-boot/204-web-beetl](spring-boot/206-web-beetl)
        - [spring & 模板引擎构建web项目](http://spring.hhui.top/spring-blog/tags/%E6%A8%A1%E6%9D%BF%E5%BC%95%E6%93%8E/)
    - 返回头
- [ ] 异常处理
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

### VI. 应用相关

#### 1. [SpringBoot应用篇Bean之注销与动态注册实现服务mock](http://spring.hhui.top/spring-blog/2018/10/17/181017-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87Bean%E4%B9%8B%E6%B3%A8%E9%94%80%E4%B8%8E%E5%8A%A8%E6%80%81%E6%B3%A8%E5%86%8C%E5%AE%9E%E7%8E%B0%E6%9C%8D%E5%8A%A1mock/)

> 通过bean的基础知识，实现动态的bean注册于销毁，通过定义接口，模拟生成mock服务，用于后续测试的演示工程， 工程源码： [https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-boot/006-dynamicbean](https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-boot/006-dynamicbean)

#### 2. [SpringBoot应用篇之FactoryBean及代理实现SPI机制的实例](http://spring.hhui.top/spring-blog/2018/10/24/181024-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8BFactoryBean%E5%8F%8A%E4%BB%A3%E7%90%86%E5%AE%9E%E7%8E%B0SPI%E6%9C%BA%E5%88%B6%E7%9A%84%E5%AE%9E%E4%BE%8B/)

> SPI在实际的业务开发中，可能很多童鞋都没有接触过，如果看一些开源项目如logback, dubbo... 会发现这个技术应用还是比较广泛的；这篇文章主要是在学习FactoryBean之后的萌发的，通过工程bean，来时先SPI的效果，这里也用到了代理的相关知识点，对于希望理解SPI、代理模式和FactoryBean的童鞋，还是比较有用的, 工程源码: [https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-case/000-spi-factorybean](https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-case/000-spi-factorybean)

#### 3. [SpringBoot应用篇之借助Redis实现排行榜功能](http://spring.hhui.top/spring-blog/2018/12/25/181225-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E5%AE%9E%E7%8E%B0%E6%8E%92%E8%A1%8C%E6%A6%9C%E5%8A%9F%E8%83%BD/)

> 排行榜可以说大量的出现在我们的视线中，那么你真的知道如何实现一个排行榜的功能么？如何保证排行榜的实时刷新，快速获取自己的排名？这篇文章就是redis学习后的典型应用，通过zset来实现排行榜功能，源码为：[https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-case/120-redis-ranklist](https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-case/120-redis-ranklist)

#### 4. [SpringCloud应用篇之AOP实现日志功能](http://spring.hhui.top/spring-blog/2019/03/13/190313-SpringCloud%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8BAOP%E5%AE%9E%E7%8E%B0%E6%97%A5%E5%BF%97%E5%8A%9F%E8%83%BD/)

> 日志可以说是后端定位问题的神器，没有日志如何找bug？但是埋点、写日志对于后端来说，就不那么友好了。这篇文章是在AOP学习之后的应用，尽量少侵入的方式，实现服务相应相关日志，主要是提供一种解耦的日志输出思路，源码可见: [https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-boot/011-aop-logaspect](https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-boot/011-aop-logaspect)

#### 5. [SpringBoot系列教程应用篇之借助Redis搭建一个简单站点统计服务](http://spring.hhui.top/spring-blog/2019/05/13/190513-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E6%90%AD%E5%BB%BA%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%AB%99%E7%82%B9%E7%BB%9F%E8%AE%A1%E6%9C%8D%E5%8A%A1/)

> 站点统计uv,pv 可以说是一个非常有意思的场景了，如何实现呢？我希望统计我的网站，总访问量，访问人数，每个人的访问排名，每天的pv/uv统计，找到我的网站中最受大家欢迎的页面，可以怎么实现？学完redis之后，忽然发现，redis的几个数据结构貌似可以搞一搞，实现这个功能，所以就有了这篇应用文章，源码为： [https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-case/124-redis-sitecount](https://github.com/liuyueyi/spring-boot-demo/tree/master/spring-case/124-redis-sitecount)


## 2. 系列博文

### 1. 配置相关

本节主要内容包括如何使用配置文件中的配置信息，配置文件中配置参数的内引用方式，刷新配置的数段以及配置变更的监听，配置参数的优先级问题，多环境中如何选用配置文件等相关内容


- [180919-SpringBoot基础篇配置信息之如何读取配置信息](/spring-blog/2018/09/19/180919-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E5%A6%82%E4%BD%95%E8%AF%BB%E5%8F%96%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF/)
- [180920-SpringBoot基础篇配置信息之多环境配置信息](/spring-blog//2018/09/20/180920-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E5%A4%9A%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF)
- [180921-SpringBoot基础篇配置信息之自定义配置指定与配置内引用](/spring-blog/2018/09/21/180921-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E9%85%8D%E7%BD%AE%E6%8C%87%E5%AE%9A%E4%B8%8E%E9%85%8D%E7%BD%AE%E5%86%85%E5%BC%95%E7%94%A8)
- [180922-SpringBoot基础篇配置信息之配置刷新](/spring-blog/2018/09/22/180922-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E9%85%8D%E7%BD%AE%E5%88%B7%E6%96%B0)
- [180925-SpringBoot基础篇配置信息之默认配置](/spring-blog/2018/09/25/180925-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE)

### 2. 日志篇


日志作为后端码农的调优、bug定位、数据分析等问题的杀手锏，可以说是每个后端都需要掌握的技能，下面主要介绍的是如何配置日志文件以及配置参数的解读 


- [180927-SpringBoot基础篇日志管理之默认配置](/spring-blog/2018/09/27/180927-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E6%97%A5%E5%BF%97%E7%AE%A1%E7%90%86%E4%B9%8B%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE/)
- [180929-SpringBoot基础篇日志管理之logback配置文件](/spring-blog/2018/09/29/180929-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E6%97%A5%E5%BF%97%E7%AE%A1%E7%90%86%E4%B9%8Blogback%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6/)


### 3. Bean(DI/IoC)基础篇



bean作为Spring生态中的基石存在，可以说在Spring体系中，一切都是bean；因此如何定义和使用Bean可以说是正确入门Spring的门槛了



#### a. 基础博文

- [181009-SpringBoot基础篇Bean之基本定义与使用](http://spring.hhui.top/spring-blog/2018/10/09/181009-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E5%9F%BA%E6%9C%AC%E5%AE%9A%E4%B9%89%E4%B8%8E%E4%BD%BF%E7%94%A8/)
- [181012-SpringBoot基础篇Bean之自动加载](http://spring.hhui.top/spring-blog/2018/10/12/181012-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E8%87%AA%E5%8A%A8%E5%8A%A0%E8%BD%BD/)
- [181013-SpringBoot基础篇Bean之动态注册](http://spring.hhui.top/spring-blog/2018/10/13/181013-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E5%8A%A8%E6%80%81%E6%B3%A8%E5%86%8C/)
- [181018-SpringBoot基础篇Bean之条件注入@Condition使用姿势](http://spring.hhui.top/spring-blog/2018/10/18/181018-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E6%9D%A1%E4%BB%B6%E6%B3%A8%E5%85%A5-Condition%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [181019-SpringBoot基础篇Bean之@ConditionalOnBean与@ConditionalOnClass](http://spring.hhui.top/spring-blog/2018/10/19/181019-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B-ConditionalOnBean%E4%B8%8E@ConditionalOnClass/)
- [181019-SpringBoot基础篇Bean之条件注入@ConditionalOnProperty](http://spring.hhui.top/spring-blog/2018/10/19/181019-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E6%9D%A1%E4%BB%B6%E6%B3%A8%E5%85%A5-ConditionalOnProperty/)
- [181019-SpringBoot基础篇Bean之条件注入@ConditionalOnExpression](http://spring.hhui.top/spring-blog/2018/10/19/181019-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87Bean%E4%B9%8B%E6%9D%A1%E4%BB%B6%E6%B3%A8%E5%85%A5-ConditionalOnExpression/)
- [191023-SpringBoot系列教程之Bean加载顺序之错误使用姿势辟谣](http://spring.hhui.top/spring-blog/2019/10/23/191023-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BBean%E5%8A%A0%E8%BD%BD%E9%A1%BA%E5%BA%8F%E4%B9%8B%E9%94%99%E8%AF%AF%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%BE%9F%E8%B0%A3/)
- [191029-SpringBoot系列教程之Bean之指定初始化顺序的若干姿势](http://spring.hhui.top/spring-blog/2019/10/29/191029-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BBean%E4%B9%8B%E6%8C%87%E5%AE%9A%E5%88%9D%E5%A7%8B%E5%8C%96%E9%A1%BA%E5%BA%8F%E7%9A%84%E8%8B%A5%E5%B9%B2%E5%A7%BF%E5%8A%BF/)

#### b. 应用博文

- [181017-SpringBoot应用篇Bean之注销与动态注册实现服务mock](http://spring.hhui.top/spring-blog/2018/10/17/181017-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87Bean%E4%B9%8B%E6%B3%A8%E9%94%80%E4%B8%8E%E5%8A%A8%E6%80%81%E6%B3%A8%E5%86%8C%E5%AE%9E%E7%8E%B0%E6%9C%8D%E5%8A%A1mock/)
- [181024-SpringBoot应用篇之FactoryBean及代理实现SPI机制的实例](http://spring.hhui.top/spring-blog/2018/10/24/181024-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8BFactoryBean%E5%8F%8A%E4%BB%A3%E7%90%86%E5%AE%9E%E7%8E%B0SPI%E6%9C%BA%E5%88%B6%E7%9A%84%E5%AE%9E%E4%BE%8B/)

### 4. AOP基础篇



AOP与IOC作为Spring最主要的两个特性，这里主要介绍下AOP的使用姿势以及一些需要注意的特性



#### a. 基础博文

- [190301-SpringBoot基础篇AOP之基本使用姿势小结](http://spring.hhui.top/spring-blog/2019/03/01/190301-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87AOP%E4%B9%8B%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E5%B0%8F%E7%BB%93/)
- [190302-SpringBoot基础篇AOP之高级使用技能](http://spring.hhui.top/spring-blog/2019/03/02/190302-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87AOP%E4%B9%8B%E9%AB%98%E7%BA%A7%E4%BD%BF%E7%94%A8%E6%8A%80%E8%83%BD/)
- [190310-SpringCloud基础篇AOP之拦截优先级详解](http://spring.hhui.top/spring-blog/2019/03/10/190310-SpringCloud%E5%9F%BA%E7%A1%80%E7%AF%87AOP%E4%B9%8B%E6%8B%A6%E6%88%AA%E4%BC%98%E5%85%88%E7%BA%A7%E8%AF%A6%E8%A7%A3/)

#### b. 应用博文

- [190313-SpringCloud应用篇之AOP实现日志功能](http://spring.hhui.top/spring-blog/2019/03/13/190313-SpringCloud%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8BAOP%E5%AE%9E%E7%8E%B0%E6%97%A5%E5%BF%97%E5%8A%9F%E8%83%BD/)


### 5. DB篇

java后端，不得不打交道的就是DB了，有常见的关系型数据库如MySql；也有一些其他的如文档数据库MongoDB, 时序数据库InfluxDB等；不同的数据库对应不同的应用场景，也因此有不同的使用姿势；总的来说，针对DB相关，需要关注的无法下面一些

- db的相关配置（即环境搭建）
- 增删改查（CURD）
- 数据库事物,分布式的一致性问题
- 读写锁，悲观锁、乐观锁等相关业务场景的支持问题
- 大数据场景下的支持（分库分表？）
- 一些辅助DB操作的开源库的使用（如mybatis,jooq,herbinate等)


- [180926-SpringBoot高级篇DB之基本使用](http://spring.hhui.top/spring-blog/2018/09/26/180926-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87DB%E4%B9%8B%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8/)
- [190407-SpringBoot高级篇JdbcTemplate之数据插入使用姿势详解](http://spring.hhui.top/spring-blog/2019/04/07/190407-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87JdbcTemplate%E4%B9%8B%E6%95%B0%E6%8D%AE%E6%8F%92%E5%85%A5%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%AF%A6%E8%A7%A3/)
- [190412-SpringBoot高级篇JdbcTemplate之数据查询上篇](http://spring.hhui.top/spring-blog/2019/04/12/190412-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87JdbcTemplate%E4%B9%8B%E6%95%B0%E6%8D%AE%E6%9F%A5%E8%AF%A2%E4%B8%8A%E7%AF%87/)
- [190417-SpringBoot高级篇JdbcTemplate之数据查询下篇](http://spring.hhui.top/spring-blog/2019/04/17/190417-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87JdbcTemplate%E4%B9%8B%E6%95%B0%E6%8D%AE%E6%9F%A5%E8%AF%A2%E4%B8%8B%E7%AF%87/)
- [190418-SpringBoot高级篇JdbcTemplate之数据更新与删除](http://spring.hhui.top/spring-blog/2019/04/18/190418-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87JdbcTemplate%E4%B9%8B%E6%95%B0%E6%8D%AE%E6%9B%B4%E6%96%B0%E4%B8%8E%E5%88%A0%E9%99%A4/)


### 6. Redis篇


redis 更常见的是作为服务的缓存来使用的，除此之外，也适用于做一些其他更富有功能特性的场景，因此对于redis而言，除了掌握基本的数据读写之外、还需要富有一些创造性的想象力

- 使用Redis来实现计数器
- 使用Redis来实现实时排行榜
- 使用Redis实现分布式锁
- ...


#### a. 基础博文

- [181029-SpringBoot高级篇Redis之基本配置](http://spring.hhui.top/spring-blog/2018/10/29/181029-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8B%E5%9F%BA%E6%9C%AC%E9%85%8D%E7%BD%AE/)
- [181101-SpringBoot高级篇Redis之Jedis配置](http://spring.hhui.top/spring-blog/2018/11/01/181101-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BJedis%E9%85%8D%E7%BD%AE/)
- [181108-SpringBoot高级篇Redis之String数据结构的读写](http://spring.hhui.top/spring-blog/2018/11/08/181108-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BString%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E7%9A%84%E8%AF%BB%E5%86%99/)
- [181109-SpringBoot高级篇Redis之List数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/11/09/181109-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BList%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [181202-SpringBoot高级篇Redis之Hash数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/02/181202-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BHash%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [181211-SpringBoot高级篇Redis之Set数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/11/181211-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BSet%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [181212-SpringBoot高级篇Redis之ZSet数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/12/181212-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BZSet%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [190927-SpringBoot系列教程之Redis集群环境配置](http://spring.hhui.top/spring-blog/2019/09/27/190927-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8BRedis%E9%9B%86%E7%BE%A4%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE/)

#### b. 应用博文

- [181225-SpringBoot应用篇之借助Redis实现排行榜功能](http://spring.hhui.top/spring-blog/2018/12/25/181225-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E5%AE%9E%E7%8E%B0%E6%8E%92%E8%A1%8C%E6%A6%9C%E5%8A%9F%E8%83%BD/)
- [190513-SpringBoot系列教程应用篇之借助Redis搭建一个简单站点统计服务](http://spring.hhui.top/spring-blog/2019/05/13/190513-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E6%90%AD%E5%BB%BA%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%AB%99%E7%82%B9%E7%BB%9F%E8%AE%A1%E6%9C%8D%E5%8A%A1/)

### 7. MongoDB篇


MongoDB是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库的，比较与传统的mysql，它可以很方便的插入文档，文档内部可以各种嵌套，用于一次获取各种关联数据非常方便，当然作为db，我们首先需要了解的依然是增删改查，因此主要内容将包括

- 基本配置
- 增删改查
- 索引相关
- ...


- [181213-SpringBoot高级篇MongoDB之基本环境搭建与使用](http://spring.hhui.top/spring-blog/2018/12/13/181213-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87MongoDB%E4%B9%8B%E5%9F%BA%E6%9C%AC%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA%E4%B8%8E%E4%BD%BF%E7%94%A8/)
- [190113-SpringBoot高级篇MongoDB之查询基本使用姿势](http://spring.hhui.top/spring-blog/2019/01/13/190113-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87MongoDB%E4%B9%8B%E6%9F%A5%E8%AF%A2%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [190124-SpringBoot高级篇MongoDB之如何新增文档](http://spring.hhui.top/spring-blog/2019/01/24/190124-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87MongoDB%E4%B9%8B%E5%A6%82%E4%BD%95%E6%96%B0%E5%A2%9E%E6%96%87%E6%A1%A3/)
- [190218-SpringBoot高级篇MongoDB之修改基本使用姿势](http://spring.hhui.top/spring-blog/2019/02/18/190218-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87MongoDB%E4%B9%8B%E4%BF%AE%E6%94%B9%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)

### 8. JPA


hibernate来操作db的系列教程，主要介绍如何根据方法命名方式来实现sql的效果

- 基本的CURD
- JQL用法
...


- [190612-SpringBoot系列教程JPA之基础环境搭建](http://spring.hhui.top/spring-blog/2019/06/12/190612-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E5%9F%BA%E7%A1%80%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
- [190614-SpringBoot系列教程JPA之新增记录使用姿势](http://spring.hhui.top/spring-blog/2019/06/14/190614-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E6%96%B0%E5%A2%9E%E8%AE%B0%E5%BD%95%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [190623-SpringBoot系列教程JPA之update使用姿势](http://spring.hhui.top/spring-blog/2019/06/23/190623-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bupdate%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [190702-SpringBoot系列教程JPA之delete使用姿势详解](http://spring.hhui.top/spring-blog/2019/07/02/190702-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bdelete%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%AF%A6%E8%A7%A3/)
- [190717-SpringBoot系列教程JPA之query使用姿势详解之基础篇](http://spring.hhui.top/spring-blog/2019/07/17/190717-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bquery%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%AF%A6%E8%A7%A3%E4%B9%8B%E5%9F%BA%E7%A1%80%E7%AF%87/)
- [191119-SpringBoot系列教程JPA之指定id保存](http://spring.hhui.top/spring-blog/2019/11/19/191119-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E6%8C%87%E5%AE%9Aid%E4%BF%9D%E5%AD%98/)


### 9. Web篇


web系列博文列表，主要介绍web应用搭建的基本知识点，包括但不限于

- 基本环境搭建
- 各种请求姿势的支持
- web三大组件
- web安全等
- websocket/reactive
...


- [190213-SpringBoot文件上传异常之提示The temporary upload location xxx is not valid](http://spring.hhui.top/spring-blog/2019/02/13/190213-SpringBoot%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E5%BC%82%E5%B8%B8%E4%B9%8B%E6%8F%90%E7%A4%BAThe-temporary-upload-location-xxx-is-not-valid/)
- [190316-Spring MVC之基于xml配置的web应用构建](http://spring.hhui.top/spring-blog/2019/03/16/190316-Spring-MVC%E4%B9%8B%E5%9F%BA%E4%BA%8Exml%E9%85%8D%E7%BD%AE%E7%9A%84web%E5%BA%94%E7%94%A8%E6%9E%84%E5%BB%BA/)
- [190317-Spring MVC之基于java config无xml配置的web应用构建](http://spring.hhui.top/spring-blog/2019/03/17/190317-Spring-MVC%E4%B9%8B%E5%9F%BA%E4%BA%8Ejava-config%E6%97%A0xml%E9%85%8D%E7%BD%AE%E7%9A%84web%E5%BA%94%E7%94%A8%E6%9E%84%E5%BB%BA/)
- [190319-SpringBoot高级篇WEB之demo应用构建](http://spring.hhui.top/spring-blog/2019/03/19/190319-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87WEB%E4%B9%8Bdemo%E5%BA%94%E7%94%A8%E6%9E%84%E5%BB%BA/)
- [190323-Spring MVC之Filter基本使用姿势](http://spring.hhui.top/spring-blog/2019/03/23/190323-Spring-MVC%E4%B9%8BFilter%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [190421-SpringBoot高级篇WEB之websocket的使用说明](http://spring.hhui.top/spring-blog/2019/04/21/190421-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87WEB%E4%B9%8Bwebsocket%E7%9A%84%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E/)
- [190816-SpringBoot系列教程web篇之Freemaker环境搭建](http://spring.hhui.top/spring-blog/2019/08/16/190816-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BFreemaker%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
- [190820-SpringBoot系列教程web篇之Thymeleaf环境搭建](http://spring.hhui.top/spring-blog/2019/08/20/190820-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BThymeleaf%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
- [190822-SpringBoot系列教程web篇之Beetl环境搭建](http://spring.hhui.top/spring-blog/2019/08/22/190822-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BBeetl%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
- [190824-SpringBoot系列教程web篇之Get请求参数解析姿势汇总](http://spring.hhui.top/spring-blog/2019/08/24/190824-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BGet%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0%E8%A7%A3%E6%9E%90%E5%A7%BF%E5%8A%BF%E6%B1%87%E6%80%BB/)
- [190828-SpringBoot系列教程web篇之Post请求参数解析姿势汇总](http://spring.hhui.top/spring-blog/2019/08/28/190828-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BPost%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0%E8%A7%A3%E6%9E%90%E5%A7%BF%E5%8A%BF%E6%B1%87%E6%80%BB/)
- [190831-SpringBoot系列教程web篇之如何自定义参数解析器](http://spring.hhui.top/spring-blog/2019/08/31/190831-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E5%A6%82%E4%BD%95%E8%87%AA%E5%AE%9A%E4%B9%89%E5%8F%82%E6%95%B0%E8%A7%A3%E6%9E%90%E5%99%A8/)
- [190905-SpringBoot系列教程web篇之中文乱码问题解决](http://spring.hhui.top/spring-blog/2019/09/05/190905-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E4%B8%AD%E6%96%87%E4%B9%B1%E7%A0%81%E9%97%AE%E9%A2%98%E8%A7%A3%E5%86%B3/)
- [190913-SpringBoot系列教程web篇之返回文本、网页、图片的操作姿势](http://spring.hhui.top/spring-blog/2019/09/13/190913-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%BF%94%E5%9B%9E%E6%96%87%E6%9C%AC%E3%80%81%E7%BD%91%E9%A1%B5%E3%80%81%E5%9B%BE%E7%89%87%E7%9A%84%E6%93%8D%E4%BD%9C%E5%A7%BF%E5%8A%BF/)
- [190929-SpringBoot系列教程web篇之重定向](http://spring.hhui.top/spring-blog/2019/09/29/190929-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E9%87%8D%E5%AE%9A%E5%90%91/)
- [190930-SpringBoot系列教程web篇之404、500异常页面配置](http://spring.hhui.top/spring-blog/2019/09/30/190930-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B404%E3%80%81500%E5%BC%82%E5%B8%B8%E9%A1%B5%E9%9D%A2%E9%85%8D%E7%BD%AE/)
- [191010-SpringBoot系列教程web篇之全局异常处理](http://spring.hhui.top/spring-blog/2019/10/10/191010-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E5%85%A8%E5%B1%80%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86/)
- [191012-SpringBoot系列教程web篇之自定义异常处理HandlerExceptionResolver](http://spring.hhui.top/spring-blog/2019/10/12/191012-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86HandlerExceptionResolver/)
- [191016-SpringBoot系列教程web篇之过滤器Filter使用指南](http://spring.hhui.top/spring-blog/2019/10/16/191016-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%BF%87%E6%BB%A4%E5%99%A8Filter%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/)
- [191018-SpringBoot系列教程web篇之过滤器Filter使用指南扩展篇](http://spring.hhui.top/spring-blog/2019/10/18/191018-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8B%E8%BF%87%E6%BB%A4%E5%99%A8Filter%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97%E6%89%A9%E5%B1%95%E7%AF%87/)
- [191120-SpringBoot系列教程Web篇之开启GZIP数据压缩](http://spring.hhui.top/spring-blog/2019/11/20/191120-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BWeb%E7%AF%87%E4%B9%8B%E5%BC%80%E5%90%AFGZIP%E6%95%B0%E6%8D%AE%E5%8E%8B%E7%BC%A9/)

### 10. 搜索篇


对互联网而言，搜索不可或缺，特别是现在讲究什么人工智能，机器学习，对于搜索，推荐，分类聚类什么的也都成了常见的名词，在企业级搜索中，常用的两个开源项目有solr和es，我们将主要介绍下，他们的基本配置与使用姿势

- 基本配置
- 增删改查
- 索引相关
- ...


- [190510-SpringBoot高级篇搜索之Solr环境搭建与简单测试](http://spring.hhui.top/spring-blog/2019/05/10/190510-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87%E6%90%9C%E7%B4%A2%E4%B9%8BSolr%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA%E4%B8%8E%E7%AE%80%E5%8D%95%E6%B5%8B%E8%AF%95/)
- [190526-SpringBoot高级篇搜索Solr之文档新增与修改使用姿势](http://spring.hhui.top/spring-blog/2019/05/26/190526-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87%E6%90%9C%E7%B4%A2Solr%E4%B9%8B%E6%96%87%E6%A1%A3%E6%96%B0%E5%A2%9E%E4%B8%8E%E4%BF%AE%E6%94%B9%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)


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