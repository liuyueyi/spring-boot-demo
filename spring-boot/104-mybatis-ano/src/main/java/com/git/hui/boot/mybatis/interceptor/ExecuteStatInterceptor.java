package com.git.hui.boot.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * https://www.cnblogs.com/tanghaorong/p/14094521.html
 *
 * 拦截器，用于输出sql日志
 * <p>
 * - xml配置注册
 * - SqlSessionFactory注册
 * - @Component默认方式
 *
 * @author yihui
 * @date 2021/7/7
 */
@Slf4j
@Component
@Intercepts(value = {@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class ExecuteStatInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // MetaObject 是 Mybatis 提供的一个用于访问对象属性的对象
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        BoundSql sql = statement.getBoundSql(invocation.getArgs()[1]);
        System.out.println(sql);

        long start = System.currentTimeMillis();
        List<ParameterMapping> list = sql.getParameterMappings();
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(sql.getParameterObject());
        List<Object> params = new ArrayList<>(list.size());
        for (ParameterMapping mapping : list) {
            params.add(Ognl.getValue(Ognl.parseExpression(mapping.getProperty()), context, context.getRoot()));
        }
        System.out.println("------------> sql: " + sql.getSql() + "\n------------> args: " + params + "------------> cost: " + (System.currentTimeMillis() - start));
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
