## 109-multi-datasource-mybatis

> 本项目实现主要参考自: [springboot-mybatis多数据源的两种整合方法](https://blog.csdn.net/tuesdayma/article/details/81081666)

本项目主要介绍了在mybatis环境下，多数据源如何配置

**基于AbstractRoutingDataSource + AOP实现动态数据源切换**

- AbstractRoutingDataSource实现动态数据源切换
- 自定义`@DS`注解 + AOP指定Mapper对应的数据源
- `ConfigurationProperties`方式支持添加数据源无需修改配置

### 系列博文

- [210110-SpringBoot系列Mybatis基于AbstractRoutingDataSource与AOP实现多数据源切换/](https://spring.hhui.top/spring-blog/2021/01/10/210110-SpringBoot%E7%B3%BB%E5%88%97Mybatis%E5%9F%BA%E4%BA%8EAbstractRoutingDataSource%E4%B8%8EAOP%E5%AE%9E%E7%8E%B0%E5%A4%9A%E6%95%B0%E6%8D%AE%E6%BA%90%E5%88%87%E6%8D%A2/)