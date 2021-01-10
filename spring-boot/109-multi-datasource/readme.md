## 109-multi-datasource

本项目主要介绍了多数据源如何配置

- 借助`@ConfigurationProperties`来获取数据源配置类`DataSourceProperties`
- 然后借助`DataSourceProperties`来创建`DataSource`实例
- 最后通过`new JdbcTemplate(DataSource)`来获取JdbcTemplate进行数据操作

### 系列博文

- [201227-SpringBoot系列JdbcTemplate之多数据源配置与使用](https://spring.hhui.top/spring-blog/2020/12/27/201227-SpringBoot%E7%B3%BB%E5%88%97JdbcTemplate%E4%B9%8B%E5%A4%9A%E6%95%B0%E6%8D%AE%E6%BA%90%E9%85%8D%E7%BD%AE%E4%B8%8E%E4%BD%BF%E7%94%A8/)