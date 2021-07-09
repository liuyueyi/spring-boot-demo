package com.git.hui.boot.mybatis.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * 拦截器，用于输出sql日志
 *
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
        String sql = statementHandler.getBoundSql().getSql();

        String args = JSONObject.toJSONString(statementHandler.getBoundSql().getParameterObject());
        Object rs;
        try {
            rs = invocation.proceed();
        } catch (Throwable e) {
            log.error("error sql: " + sql, e);
            throw e;
        } finally {
            long cost = System.currentTimeMillis() - time;
            sql = this.replaceContinueSpace(sql);
            // 这个方法的总耗时
            log.info("\n\n ============= \nsql ----> {}\narg ----> {}\ncost ----> {}\n ============= \n", sql, args, cost);
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
