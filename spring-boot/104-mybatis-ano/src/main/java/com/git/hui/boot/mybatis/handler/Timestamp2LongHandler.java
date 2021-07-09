package com.git.hui.boot.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

/**
 * 自定义类型转换：将数据库中的日期类型，转换成long类型的时间戳
 *
 * 三种注册方式：
 * 1.直接在 result标签中，指定typeHandler，如@Result(property = "updateAt", column = "update_at", jdbcType = JdbcType.TIMESTAMP, typeHandler = Timestamp2LongHandler.class)
 * 2.在SqlSessionFactory实例中，注册 在SqlSessionFactory实例中.setTypeHandlers(new Timestamp2LongHandler());
 * 3.xml配置，<typeHandler handler="com.git.hui.boot.mybatis.handler.Timestamp2LongHandler"/>
 *
 * @author yihui
 * @date 2021/7/7
 */
@MappedTypes(value = Long.class)
@MappedJdbcTypes(value = {JdbcType.DATE, JdbcType.TIME, JdbcType.TIMESTAMP})
public class Timestamp2LongHandler extends BaseTypeHandler<Long> {

    /**
     * 将java类型，转换为jdbc类型
     *
     * @param preparedStatement
     * @param i
     * @param aLong             毫秒时间戳
     * @param jdbcType          db字段类型
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Long aLong, JdbcType jdbcType) throws SQLException {
        if (jdbcType == JdbcType.DATE) {
            preparedStatement.setDate(i, new Date(aLong));
        } else if (jdbcType == JdbcType.TIME) {
            preparedStatement.setTime(i, new Time(aLong));
        } else if (jdbcType == JdbcType.TIMESTAMP) {
            preparedStatement.setTimestamp(i, new Timestamp(aLong));
        }
    }

    @Override
    public Long getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return parse2time(resultSet.getObject(s));
    }

    @Override
    public Long getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return parse2time(resultSet.getObject(i));
    }

    @Override
    public Long getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return parse2time(callableStatement.getObject(i));
    }

    private Long parse2time(Object value) {
        if (value instanceof Date) {
            return ((Date) value).getTime();
        } else if (value instanceof Time) {
            return ((Time) value).getTime();
        } else if (value instanceof Timestamp) {
            return ((Timestamp) value).getTime();
        }
        return null;
    }
}
