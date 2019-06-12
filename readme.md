# Spring-Boot-Demo

[![Build Status](https://travis-ci.org/liuyueyi/spring-boot-demo.svg?branch=master)](https://travis-ci.org/liuyueyi/spring-boot-demo)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/liuyueyi/spring-boot-demo.svg)](http://isitmaintained.com/project/liuyueyi/spring-boot-demo "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/liuyueyi/spring-boot-demo.svg)](http://isitmaintained.com/project/liuyueyi/spring-boot-demo "Percentage of issues still open")

SpringBoot与SpringCloud学习过程中的源码汇总，沉淀记录下学习历程

## 1. 博文相关

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
    - [x] jdbcTemplate
    - [x] JPA
    - [ ] mybatis
    - [ ] hibernate
    - [ ] jooq
- [ ] influxdb 时序数据库
- [ ] [Mongo](http://spring.hhui.top/spring-blog/tags/Mongo/)
    - [x] 项目工程
        - 基础环境 [spring-boot/110-mongo-basic](spring-boot/110-mongo-basic)
        - mongoTemplate使用姿势 [spring-boot/111-mongo-template](spring-boot/111-mongo-template)
- [x] [Redis读写](http://spring.hhui.top/spring-blog/tags/Redis/
    - [x] 项目工程：
        - 基本环境构建 [spring-boot/120-redis-config](spring-boot/120-redis-config)
        - jedis环境构建  [spring-boot/121-redis-jedis-config](spring-boot/121-redis-jedis-config)
        - redisTemplate使用姿势 [spring-boot/122-redis-template](spring-boot/122-redis-template)
        - lettuce环境构建 [spring-boot/123-redis-lettuce-config](spring-boot/123-redis-lettuce-config)
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

- [ ] mvc
- [ ] 过滤器 & 拦截器
- [ ] RequestHeader, ResponseHeader相关设置
- [ ] Get/Post/Put/Delete等http方法支持
- [ ] 参数绑定
- [ ] 数据返回/视图绑定/重定向
- [ ] 异常处理
- [ ] 安全相关(SQL/XSS等注入)
- [ ] 跨域处理
- [ ] WebSocket
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

## 2. 其他

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