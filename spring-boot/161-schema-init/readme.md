# 161-schema-init

项目初始化，自动执行创建数据库、新建表、导入初始数据的示例demo

**关键点**

### DataSourceInitializer

借助`DataSourceInitializer`的`DatabasePopulator`来实现填充、初始化或清理数据库的策略

相关方法

public void addScript(Resource script)

> 添加一个用于初始化或清理数据库的外部资源

public void addScripts(Resource... scripts)

> 添加多个用于初始化或清理数据库的外部资源

public void setScripts(Resource... scripts)

> 设置用于初始化或清理数据库的外部资源，并替代之前所有的添加的资源

public void setSqlScriptEncoding(String sqlScriptEncoding)

> 如果sql脚本与项目编码不一致，用于设置sql脚本的编码

public void setSeparator(String separator)

> 设置脚本中sql的分隔符，默认是英文分号（“;”）

public void setCommentPrefix(String commentPrefix)

> 设置注释的前缀，默认是两个中划线（“--”）

public void setBlockCommentStartDelimiter(String blockCommentStartDelimiter)

> 设置段落注释的开始标记，默认是“/*”

public void setBlockCommentEndDelimiter(String blockCommentEndDelimiter)

> 设置段落注释的结束标记，默认是“*/”

public void setContinueOnError(boolean continueOnError)

> 设置当出错是是否继续，默认是false即不继续

public void setIgnoreFailedDrops(boolean ignoreFailedDrops)

> 设置是否忽略删除错误，默认是false即不忽略

public void populate(Connection connection) throws ScriptException

> 进行初始化或清理数据库。

public void execute(DataSource dataSource) throws ScriptException

> 根据给定的数据源执行ResourceDatabasePopulator


### 创建数据库

当库不存在时，SpringBoot配置的数据库的url可能就直接连不上；因此我们可以考虑先进性探测，所数据库不存在，则创建一个数据库

```sql
select schema_name from information_schema.schemata where schema_name = 数据库名;

-- 若没有，则尝试创建
create database if not exist 数据库名;
```

### 项目启动后执行

再项目启动之后，验证下是否库表初始化完成

实现接口 `ApplicationRunner`

### 系列博文


* [221221-SpringBoot系列之数据库初始化-datasource配置方式 | 一灰灰Blog](https://spring.hhui.top/spring-blog/2022/12/21/221221-SpringBoot%E7%B3%BB%E5%88%97%E4%B9%8B%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%9D%E5%A7%8B%E5%8C%96-datasource%E9%85%8D%E7%BD%AE%E6%96%B9%E5%BC%8F/)
* [221221-SpringBoot系列之数据库初始化-jpa配置方式 | 一灰灰Blog](https://spring.hhui.top/spring-blog/2022/12/21/221221-SpringBoot%E7%B3%BB%E5%88%97%E4%B9%8B%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%9D%E5%A7%8B%E5%8C%96-jpa%E9%85%8D%E7%BD%AE%E6%96%B9%E5%BC%8F/)
* [221221-SpringBoot系列之数据库初始化-DataSourceInitializer方式 | 一灰灰Blog](https://spring.hhui.top/spring-blog/2022/12/21/221221-SpringBoot%E7%B3%BB%E5%88%97%E4%B9%8B%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%9D%E5%A7%8B%E5%8C%96-DataSourceInitializer%E6%96%B9%E5%BC%8F/)
