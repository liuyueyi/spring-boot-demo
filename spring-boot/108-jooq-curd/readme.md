## 108-jooq-curd

如果通过idea的maven插件，自动生成代码出现问题，可以通过执行`mvn compile`来生成表结构数据

本工程主要介绍了JOOQ的curd基本姿势，包括单表、多表操作，函数聚合操作，事务相关，基本覆盖jooq的90%业务场景

### 系列博文

- [201213-SpringBoot系列Jooq之事务](https://spring.hhui.top/spring-blog/2020/12/13/201213-SpringBoot%E7%B3%BB%E5%88%97Jooq%E4%B9%8B%E4%BA%8B%E5%8A%A1/)
- [201211-SpringBoot系列Jooq之多表联合查询](https://spring.hhui.top/spring-blog/2020/12/11/201211-SpringBoot%E7%B3%BB%E5%88%97Jooq%E4%B9%8B%E5%A4%9A%E8%A1%A8%E8%81%94%E5%90%88%E6%9F%A5%E8%AF%A2/)
- [201210-SpringBoot系列Jooq之常用函数使用姿势](https://spring.hhui.top/spring-blog/2020/12/10/201210-SpringBoot%E7%B3%BB%E5%88%97Jooq%E4%B9%8B%E5%B8%B8%E7%94%A8%E5%87%BD%E6%95%B0%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [201204-SpringBoot系列Jooq之聚合查询](https://spring.hhui.top/spring-blog/2020/12/05/201204-SpringBoot%E7%B3%BB%E5%88%97Jooq%E4%B9%8B%E8%81%9A%E5%90%88%E6%9F%A5%E8%AF%A2/)
- [201203-SpringBoot系列Jooq之记录查询基础篇](https://spring.hhui.top/spring-blog/2020/12/03/201203-SpringBoot%E7%B3%BB%E5%88%97Jooq%E4%B9%8B%E8%AE%B0%E5%BD%95%E6%9F%A5%E8%AF%A2%E5%9F%BA%E7%A1%80%E7%AF%87/)
- [201202-SpingBoot系列Jooq批量写入采坑记录](https://spring.hhui.top/spring-blog/2020/12/02/201202-SpingBoot%E7%B3%BB%E5%88%97Jooq%E6%89%B9%E9%87%8F%E5%86%99%E5%85%A5%E9%87%87%E5%9D%91%E8%AE%B0%E5%BD%95/)
- [200930-SpringBoot系列Jooq之记录更新与删除](https://spring.hhui.top/spring-blog/2020/09/30/200930-SpringBoot%E7%B3%BB%E5%88%97Jooq%E4%B9%8B%E8%AE%B0%E5%BD%95%E6%9B%B4%E6%96%B0%E4%B8%8E%E5%88%A0%E9%99%A4/)
- [200920-SpringBoot系列Jooq之新增记录使用姿势](http://spring.hhui.top/spring-blog/2020/09/20/200920-SpringBoot%E7%B3%BB%E5%88%97Jooq%E4%B9%8B%E6%96%B0%E5%A2%9E%E8%AE%B0%E5%BD%95%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [200916-SpringBoot系列Jooq代码自动生成](http://spring.hhui.top/spring-blog/2020/09/16/200916-SpringBoot%E7%B3%BB%E5%88%97Jooq%E4%BB%A3%E7%A0%81%E8%87%AA%E5%8A%A8%E7%94%9F%E6%88%90/)
- [200915-SpringBoot系列Jooq初体验](http://spring.hhui.top/spring-blog/2020/09/15/200915-SpringBoot%E7%B3%BB%E5%88%97Jooq%E5%88%9D%E4%BD%93%E9%AA%8C/)
