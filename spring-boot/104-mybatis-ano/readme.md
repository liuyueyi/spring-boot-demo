## 103-mybatis-noxml

springboot 整合 mybatis + 注解配置方式实例demo，对应的博文:


### 1. 自定义类型转换

通过继承BaseTypeHandler来实现db类型与java类型的转换，三种注册方式

* 1.直接在 result标签中，指定typeHandler，如`@Result(property = "updateAt", column = "update_at", jdbcType = JdbcType.TIMESTAMP, typeHandler = Timestamp2LongHandler.class)`
* 2.在SqlSessionFactory实例中，注册 在SqlSessionFactory实例中`.setTypeHandlers(new Timestamp2LongHandler());`
* 3.xml配置，`<typeHandler handler="com.git.hui.boot.mybatis.handler.Timestamp2LongHandler"/>`

### 2. plugins

在映射语句执行过程中的某一点进行拦截调用，可拦截的点为

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

通过实现`Interceptor`接口，指定需要拦截的方法签名

**注册方式**

- xml文件

```xml
<plugins>
    <plugin interceptor="com.git.hui.boot.mybatis.interceptor.SqlStatInterceptor"/>
</plugins>
```

- 在SqlSessionFactory中注册 `.setPlugins(new SqlStatInterceptor());`

**使用说明**

```java
@Intercepts(value = {@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
```

注意 Signature 注解中的几个

- type: 表示拦截的接口
- method: 拦截的接口中的方法
- args: 拦截接口方法参数

上面拦截的就是下面两个方法的执行

```
// 更新
org.apache.ibatis.executor.Executor.update
// 查询
org.apache.ibatis.executor.Executor.query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler)
```