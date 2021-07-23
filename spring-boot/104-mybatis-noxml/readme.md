## 103-mybatis-noxml

springboot 整合 mybatis + 注解配置方式实例demo，对应的博文:

- [191230-SpringBoot系列教程Mybatis-注解整合篇](http://spring.hhui.top/spring-blog/2019/12/30/191230-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BMybatis-%E6%B3%A8%E8%A7%A3%E6%95%B4%E5%90%88%E7%AF%87/)


---

> https://zhuanlan.zhihu.com/p/265852739
> https://zhuanlan.zhihu.com/p/46647057

Sql 优化

- 外功
    - 硬件层
        - 网络带宽
        - 硬盘，内存，服务器性能
    - 系统配置 
    - 使用姿势：
        - 合理使用数据库连接池
- 内功
    - 存储引擎
    - 数据库表结构
    - SQL及索引
    
原则：

- 减少数据访问： 设置合理的字段类型，启用压缩，通过索引访问等减少磁盘IO
- 返回更少的数据： 只返回需要的字段和数据分页处理 减少磁盘io及网络io
- 减少交互次数： 批量DML操作，函数存储等减少数据连接次数
- 减少服务器CPU开销： 尽量减少数据库排序操作以及全表查询，减少cpu 内存占用
- 利用更多资源： 使用表分区，可以增加并行操作，更大限度利用cpu资源

引擎：

- InnoDB
- MyISAM
- 内存
- ...

建表：

- 三范式
- 反范式
- 如何选择索引
- 类型选择：数字 > 字符串
- 用varchar/nvarchar 代替 char/nchar

SQL语句

- 优化原则：走索引
- 锁竞争
- group by优化
- join 优化
- union/union all -> 不建议使用
- 复杂sql拆分，避免大事务
- 清空全表: 使用truncate代替delete
- 分页
- 索引不生效的场景
- 分库分表

实例演示：

单表sql

- 大分页
- 排序 + 大分页
- 如博文关键词查询，实现搜索功能
- 多表的联合的优化

工具介绍：

- show log: 慢查询日志
- mysqldumpslow 
- explain

