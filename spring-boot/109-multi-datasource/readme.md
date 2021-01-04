## 109-multi-datasource

本项目主要介绍了多数据源如何配置

- 借助`@ConfigurationProperties`来获取数据源配置类`DataSourceProperties`
- 然后借助`DataSourceProperties`来创建`DataSource`实例
- 最后通过`new JdbcTemplate(DataSource)`来获取JdbcTemplate进行数据操作

### 系列博文

