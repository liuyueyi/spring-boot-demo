## 103-mybatis-xml

springboot 整合 mybatis + xml配置方式实例demo，对应的博文:

- [191227-SpringBoot系列教程Mybatis-xml整合篇](http://spring.hhui.top/spring-blog/2019/12/27/191227-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BMybatis-xml%E6%95%B4%E5%90%88%E7%AF%87/)


Mapper接口与sql文件映射的四种姿势
===

- 默认：在resource资源目录下，xml文件的目录层级与Mapper接口的包层级完全一致，且xml文件名与mapper接口文件名也完全一致
    - 如mapper接口： `com.git.hui.boot.mybatis.mapper.MoneyMapper`
    - 对应的xml文件:  `com/git/hui/boot/mybatis/mapper/MoneyMapper.xml`
- springboot配置参数:
    - application.yml配置文件中，指定 `mybatis.mapper-locations=classpath:sqlmapper/*.xml`
- mybatis-config配置文件
    - 这种姿势常见于非SpringBoot项目集成mybatis，通常将mybatis的相关配置放在 `mybatis-config.xml` 文件中
    - 首先在配置文件中，指定加载参数 `mybatis.config-location=classpath:mybatis-config.xml`
    - 然后指定映射器 ` <mappers><mapper resource="sqlmapper/money-mapper.xml"/></mappers>`
- SqlSessionFactory指定
    - 直接在SqlSessionFactory中指定即可Mapper文件

```java
// 设置mybatis的xml所在位置，这里使用mybatis注解方式，没有配置xml文件
bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
```

---

类型别名:
===

- 在 `mybatis-config.xml` 文件中指定，这样默认的别名为首字母小写的case, 如 `MoneyPo` 的别名就是 `moneyPo`

```xml
<typeAliases>
    <!-- 类型别名   -->
    <package name="com.git.hui.boot.mybatis.entity"/>
</typeAliases>
```

- 依然是在上面的xml文件中，指定别名

```xml
<typeAliases>
    <typeAlias alias="MoneyPo" type="com.git.hui.boot.mybatis.entity.MoneyPo"/>
</typeAliases>
```
- 使用注解 `@Alias`，放在PO对象上

参数替换
===

`#{}` 参数替换时，怎么确定传参类型？如果db中字段为varchar, 传入的是一个int，那么最终的sql是怎么确定的？ 如果传入的是string，最终的sql是在什么地方带上''的

```sql
select * from money where `name` = #{name}
-- 注意，下面虽然指定了db字段的类型，但是如果传入的name为整数，最终拼接的sql，也不会携带上'', 它主要适用于传入null时，类型转换可能出现的异常
select * from money where `name` = #{name, jdbcType=VARCHAR}
```

- 如果传入的参数 `name = 1`，那么最终的sql为 `select * from money where name = 1`
- 如果传入的参数 `name = '1'`，那么最终的sql为 `select * from money where name = '1'`

如果我希望即使传入的是整数，最终凭借的sql，也是字符串类型的， 可以如下操作

```sql
select * from money where `name` = #{name, jdbcType=VARCHAR, typeHandler=xxx}
```

关于上面的几个知识点，如何验证呢？ 可以在mysql断开启日志记录，可以查看mysql所有执行的sql

```bash
# 开启日志
set global general_log = "ON";
# 查询日志文件路径
show variables like 'general_log%';
```

然后在日志文件中，查看上面几种类型的sql，最终发送到mysql执行时的语句是啥即可

接下来的重点就是确认一下，sql拼装的实现逻辑
- DefaultParameterHandler 默认的参数处理类，这里实现参数的替换
- UnknownTypeHandler: 默认的类型转换，这里是关键，会根据传参的类型，来决定具体的TypeHandler，
    - org.apache.ibatis.type.UnknownTypeHandler.resolveTypeHandler(java.lang.Object, org.apache.ibatis.type.JdbcType)
- 最终的sql拼接，主要借助的是`com.mysql.cj.jdbc.StatementImpl.query`
    - `com.mysql.cj.ClientPreparedQuery` 
    - `com.mysql.cj.AbstractPreparedQuery.asSql(boolean)`

---

枚举字段
===

> https://www.twle.cn/c/yufei/mysqlfav/mysqlfav-basic-enum2.html

对于枚举字段，需要额外注意，传入的查询条件，请使用字符串，如果传参类型为整数，那么表示根据枚举的下标进行查询(下标从1开始)

- 如 enum bank("2", "3", "1")
- 查询条件 bank = 1，实际等价于 bank = '2'， 查询枚举值为"2"的记录；而不是枚举值为"1"的记录

原则上，不要在mysql中定义枚举类型的字段