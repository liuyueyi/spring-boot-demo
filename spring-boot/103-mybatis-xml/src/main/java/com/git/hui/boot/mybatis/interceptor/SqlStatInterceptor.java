package com.git.hui.boot.mybatis.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author yihui
 * @date 2021/1/26
 */
@Slf4j
//@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})})
public class SqlStatInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long time = System.currentTimeMillis();
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String sql = statementHandler.getBoundSql().getSql();

        String args = JSONObject.toJSONString(statementHandler.getBoundSql().getParameterObject());

//        Statement statement = (Statement) invocation.getArgs()[0];
//        if (Proxy.isProxyClass(statement.getClass())) {
//            PreparedStatementLogger preparedStatementLogger = (PreparedStatementLogger) Proxy.getInvocationHandler(statement);
//            Field field = ReflectionUtils.findField(preparedStatementLogger.getClass(), "columnValues");
//            field.setAccessible(true);
//            Object values = ReflectionUtils.getField(field, preparedStatementLogger);
//            args = JSONObject.toJSONString(values);
//        }

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
