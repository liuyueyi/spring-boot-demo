//package com.git.hui.boot.mybatis.interceptor;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.*;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.lang.reflect.Method;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//import java.util.regex.Matcher;
//
///**
// * <a href='https://xie.infoq.cn/article/5b2a73968569941875c1d8057'/>
// *
// * @author yihui
// * @date 2021/9/24
// */
//@Slf4j
//@Component
//@Intercepts({
//        @Signature(type = Executor.class, method = "query",
//                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//        @Signature(type = Executor.class, method = "query",
//                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
//})
//public class MysqlExplainInterceptor implements Interceptor {
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        // do nothing
//    }
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object target = invocation.getTarget();
//        Object[] args = invocation.getArgs();
//        if (target instanceof Executor) {
//            final Executor executor = (Executor) target;
//            Object parameter = args[1];
//            boolean isUpdate = args.length == 2;
//            MappedStatement ms = (MappedStatement) args[0];
//            if (!isUpdate && ms.getSqlCommandType() == SqlCommandType.SELECT) {
//                RowBounds rowBounds = (RowBounds) args[2];
//                ResultHandler resultHandler = (ResultHandler) args[3];
//                BoundSql boundSql;
//                if (args.length == 4) {
//                    boundSql = ms.getBoundSql(parameter);
//                } else {
//                    // 几乎不可能走进这里面,除非使用Executor的代理对象调用query[args[6]]
//                    boundSql = (BoundSql) args[5];
//                }
//                String sql = getSql(boundSql, ms);
//                log.info("sql = {}", sql);
//                Statement stmt = executor.getTransaction().getConnection().createStatement();
//                stmt.execute("EXPLAIN " + sql + " ;");
//                ResultSet rs = stmt.getResultSet();
//
//                List<ExplainResultVo> explainResultList = new ArrayList<>();
//                while (rs.next()) {
//                    ExplainResultVo explainResultVo = new ExplainResultVo();
//                    explainResultVo.setId(rs.getString("id"));
//                    explainResultVo.setSelectType(rs.getString("select_type"));
//                    explainResultVo.setTable(rs.getString("table"));
//                    explainResultVo.setPartitions(rs.getString("partitions"));
//                    explainResultVo.setType(rs.getString("type"));
//                    explainResultVo.setPossibleKeys(rs.getString("possible_keys"));
//                    explainResultVo.setKey(rs.getString("key"));
//                    explainResultVo.setKeyLen(rs.getString("key_len"));
//                    explainResultVo.setRef(rs.getString("ref"));
//                    explainResultVo.setRows(rs.getString("rows"));
//                    explainResultVo.setFiltered(rs.getString("filtered"));
//                    explainResultVo.setExtra(rs.getString("Extra"));
//                    explainResultList.add(explainResultVo);
//                }
//
//                log.info("*************************** 1. row ***************************");
//                for (ExplainResultVo resultVo : explainResultList) {
//                    Method[] methods = resultVo.getClass().getDeclaredMethods();
//                    for (Method method : methods) {
//                        if (!method.getName().startsWith("get")) {
//                            continue;
//                        }
//                        Object result = method.invoke(resultVo);
//                        if (result instanceof String) {
//                            log.info(method.getName() + " : " + result);
//                        }
//                    }
//                }
//                log.info("*************************** end ***************************");
//            }
//        }
//        return invocation.proceed();
//    }
//
//    /**
//     * 生成要执行的SQL命令
//     *
//     * @param boundSql
//     * @param ms
//     * @return
//     */
//    private String getSql(BoundSql boundSql, MappedStatement ms) {
//        String sql = boundSql.getSql();
//        Object parameterObject = boundSql.getParameterObject();
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
//            for (int i = 0; i < parameterMappings.size(); i++) {
//                ParameterMapping parameterMapping = parameterMappings.get(i);
//                if (parameterMapping.getMode() != ParameterMode.OUT) {
//                    //参数值
//                    Object value;
//                    //获取参数名称
//                    String propertyName = parameterMapping.getProperty();
//                    if (boundSql.hasAdditionalParameter(propertyName)) {
//                        //获取参数值
//                        value = boundSql.getAdditionalParameter(propertyName);
//                    } else if (parameterObject == null) {
//                        value = null;
//                    } else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
//                        //如果是单个值则直接赋值
//                        value = parameterObject;
//                    } else {
//                        MetaObject metaObject = ms.getConfiguration().newMetaObject(parameterObject);
//                        value = metaObject.getValue(propertyName);
//                    }
//                    log.info("value = {}", value);
//                    sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameter(value)));
//                }
//            }
//
//        }
//        return sql;
//    }
//
//    public String getParameter(Object parameter) {
//        if (parameter instanceof String) {
//            return "'" + parameter.toString() + "'";
//        }
//        return parameter.toString();
//    }
//
//    @Data
//    public static class ExplainResultVo {
//        private String id;
//        private String selectType;
//        private String table;
//        private String partitions;
//        private String type;
//        private String possibleKeys;
//        private String key;
//        private String keyLen;
//        private String ref;
//        private String rows;
//        private String filtered;
//        private String extra;
//    }
//}
//
