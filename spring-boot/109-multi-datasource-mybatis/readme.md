## 109-multi-datasource-mybatis

> 本项目实现主要参考自: [springboot-mybatis多数据源的两种整合方法](https://blog.csdn.net/tuesdayma/article/details/81081666)

本项目主要介绍了在mybatis环境下，多数据源如何配置

**基于包路径的分别配置**

- 借助`@ConfigurationProperties`来获取数据源配置类`DataSourceProperties`
- 然后借助`DataSourceProperties`来创建`DataSource`实例
- 基于`DataSource`创建`SqlSessionFactory`，并指定mapper对应的xml文件路径
- 创建`new SqlSessionTemplate(SqlSessionFactory)`

### 系列博文

- [210109-SpringBoot系列Mybatis多数据源配置与使用](https://spring.hhui.top/spring-blog/2021/01/09/210109-SpringBoot%E7%B3%BB%E5%88%97Mybatis%E5%A4%9A%E6%95%B0%E6%8D%AE%E6%BA%90%E9%85%8D%E7%BD%AE%E4%B8%8E%E4%BD%BF%E7%94%A8/)