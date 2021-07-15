package com.git.hui.boot.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
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
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})})
public class SqlStatInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // MetaObject 是 Mybatis 提供的一个用于访问对象属性的对象
        MetaObject metaObject = SystemMetaObject.forObject(invocation);
        System.out.println("当前拦截到的对象：" + metaObject.getValue("target"));
        System.out.println("SQL语句：" + metaObject.getValue("target.delegate.boundSql.sql"));
        System.out.println("SQL语句入参：" + metaObject.getValue("target.delegate.parameterHandler.parameterObject"));
        System.out.println("SQL语句类型：" + metaObject.getValue("target.delegate.parameterHandler.mappedStatement.sqlCommandType"));
        System.out.println("Mapper方法全路径名：" + metaObject.getValue("target.delegate.parameterHandler.mappedStatement.id"));

        long time = System.currentTimeMillis();
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        BoundSql sql = statementHandler.getBoundSql();
        DefaultParameterHandler handler = (DefaultParameterHandler) statementHandler.getParameterHandler();
        Field field = handler.getClass().getDeclaredField("configuration");
        field.setAccessible(true);
        Configuration configuration = (Configuration) ReflectionUtils.getField(field, handler);
        MetaObject mo = configuration.newMetaObject(sql.getParameterObject());
        List<Object> args = new ArrayList<>();
        for (ParameterMapping key : sql.getParameterMappings()) {
            args.add(mo.getValue(key.getProperty()));
        }

        Object rs;
        try {
            rs = invocation.proceed();
        } catch (Throwable e) {
            log.error("error sql: " + sql.getSql(), e);
            throw e;
        } finally {
            long cost = System.currentTimeMillis() - time;
            String sSql = this.replaceContinueSpace(sql.getSql());
            // 这个方法的总耗时
            log.info("\n\n ============= \nsql ----> {}\narg ----> {}\ncost ----> {}\n ============= \n", sSql, args, cost);
        }

        return rs;
    }

    /**
     * 替换连续的空白
     *
     * @param str
     * @return
     */
    private String replaceContinueSpace(String str) {
        StringBuilder builder = new StringBuilder(str.length());
        boolean preSpace = false;
        for (int i = 0, len = str.length(); i < len; i++) {
            char ch = str.charAt(i);
            boolean isSpace = Character.isWhitespace(ch);
            if (preSpace && isSpace) {
                continue;
            }

            if (preSpace) {
                // 前面的是空白字符，当前的不是空白字符
                preSpace = false;
                builder.append(ch);
            } else if (isSpace) {
                // 当前字符为空白字符，前面的那个不是的
                preSpace = true;
                builder.append(" ");
            } else {
                // 前一个和当前字符都非空白字符
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
