## spring-boot-jpa module

本项目主要几种演示了jpa的使用姿势，包括但不限于

- jpa环境
- 基础的crud操作
- 更加灵活的查询方式
- 高级使用技巧
- 事物相关

### 1. 数据准备

在测试这个demo之前，请先准备数据，比如安装mysql，创建对应的数据，设置用户名密码等


本篇jpa主要演示基于mysql的操作方式，因此搭建一个本机or可以测试的mysql服务端是必不可少的，准备完毕之后，可以根据自己的实际需要修改 `application.properties` 文件中的配置

本项目中，默认配置如下

```properties
## DataSource
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/story?useUnicode=true&characterEncoding=UTF-8&useSSL=false
spring.datasource.driver-class-name= com.mysql.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=
```

数据库名为 `story`， 使用的表名为 `money` (有点俗... 但是后面说到事物的时候，还是拿人的存款进行说明比较有冲击力)，建表语句

```sql
CREATE TABLE `money` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `money` int(26) NOT NULL DEFAULT '0' COMMENT '钱',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;
```

测试数据如下:

```sql
INSERT INTO `money` (`id`, `name`, `money`, `is_deleted`, `create_at`, `update_at`)
VALUES
	(1, '一灰灰blog', 100, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(2, '一灰灰2', 200, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(3, '一灰灰3', 300, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(4, '一灰灰4', 400, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(5, '一灰灰5', 500, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(6, 'Batch 一灰灰blog', 100, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(7, 'Batch 一灰灰blog 2', 100, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(8, 'Batch 一灰灰 3', 200, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(9, 'Batch 一灰灰 4', 200, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(10, 'batch 一灰灰5', 1498, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:58'),
	(11, 'batch 一灰灰6', 1498, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:58'),
	(12, 'batch 一灰灰7', 400, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40'),
	(13, 'batch 一灰灰8', 400, 0, '2019-04-18 17:01:40', '2019-04-18 17:01:40');
```

### 2. 相关博文

- [190612-SpringBoot系列教程JPA之基础环境搭建](http://spring.hhui.top/spring-blog/2019/06/12/190612-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E5%9F%BA%E7%A1%80%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/)
- [190614-SpringBoot系列教程JPA之新增记录使用姿势](http://spring.hhui.top/spring-blog/2019/06/14/190614-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E6%96%B0%E5%A2%9E%E8%AE%B0%E5%BD%95%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [190623-SpringBoot系列教程JPA之update使用姿势](http://spring.hhui.top/spring-blog/2019/06/23/190623-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bupdate%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [190702-SpringBoot系列教程JPA之delete使用姿势详解](http://spring.hhui.top/spring-blog/2019/07/02/190702-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bdelete%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%AF%A6%E8%A7%A3/)
- [190717-SpringBoot系列教程JPA之query使用姿势详解之基础篇](http://spring.hhui.top/spring-blog/2019/07/17/190717-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8Bquery%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E8%AF%A6%E8%A7%A3%E4%B9%8B%E5%9F%BA%E7%A1%80%E7%AF%87/)
- [191119-SpringBoot系列教程JPA之指定id保存](http://spring.hhui.top/spring-blog/2019/11/19/191119-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BJPA%E4%B9%8B%E6%8C%87%E5%AE%9Aid%E4%BF%9D%E5%AD%98/)

