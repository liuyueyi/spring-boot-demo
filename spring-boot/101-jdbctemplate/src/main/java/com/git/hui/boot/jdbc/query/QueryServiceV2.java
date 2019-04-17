package com.git.hui.boot.jdbc.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author yihui in 20:34 19/4/15.
 */
@Component
public class QueryServiceV2 {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void queryForRowSet() {
        String sql = "select * from money where id > 1 limit 2";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            MoneyPO moneyPO = new MoneyPO();
            moneyPO.setId(result.getInt("id"));
            moneyPO.setName(result.getString("name"));
            moneyPO.setMoney(result.getInt("money"));
            moneyPO.setDeleted(result.getBoolean("is_deleted"));
            moneyPO.setCreated(result.getDate("create_at").getTime());
            moneyPO.setUpdated(result.getDate("update_at").getTime());

            System.out.println("QueryForRowSet by DirectSql: " + moneyPO);
        }


        // 采用占位符方式查询

        sql = "select * from money where id > ? limit ?";
        result = jdbcTemplate.queryForRowSet(sql, 1, 2);
        while (result.next()) {
            MoneyPO moneyPO = new MoneyPO();
            moneyPO.setId(result.getInt("id"));
            moneyPO.setName(result.getString("name"));
            moneyPO.setMoney(result.getInt("money"));
            moneyPO.setDeleted(result.getBoolean("is_deleted"));
            moneyPO.setCreated(result.getDate("create_at").getTime());
            moneyPO.setUpdated(result.getDate("update_at").getTime());

            System.out.println("QueryForRowSet by ? sql: " + moneyPO);
        }
    }


    public void query() {
        queryByCallBack();
        queryByResultSet();
        queryByRowMapper();
        queryByPlaceHolder();
        queryByPreparedStatement();
        queryNoRecord();
    }

    private MoneyPO result2po(ResultSet result) throws SQLException {
        MoneyPO moneyPO = new MoneyPO();
        moneyPO.setId(result.getInt("id"));
        moneyPO.setName(result.getString("name"));
        moneyPO.setMoney(result.getInt("money"));
        moneyPO.setDeleted(result.getBoolean("is_deleted"));
        moneyPO.setCreated(result.getDate("create_at").getTime());
        moneyPO.setUpdated(result.getDate("update_at").getTime());
        return moneyPO;
    }

    private void queryByCallBack() {
        String sql = "select * from money where id > 1 limit 2";
        // 这个是回调方式，不返回结果；一条记录回调一次
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                MoneyPO moneyPO = result2po(rs);
                System.out.println("queryByCallBack: " + moneyPO);
            }
        });
    }

    private void queryByResultSet() {
        String sql = "select * from money where id > 1 limit 2";
        // extractData 接收的是批量的结果，因此可以理解为一次对所有的结果进行转换，可以和 RowMapper 方式进行对比
        List<MoneyPO> result = jdbcTemplate.query(sql, new ResultSetExtractor<List<MoneyPO>>() {
            @Override
            public List<MoneyPO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<MoneyPO> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(result2po(rs));
                }
                return list;
            }
        });

        System.out.println("queryByResultSet: " + result);
    }

    private void queryByRowMapper() {
        String sql = "select * from money where id > 1 limit 2";
        // 如果返回的是多条数据，会逐一的调用 mapRow方法，因此可以理解为单个记录的转换
        List<MoneyPO> result = jdbcTemplate.query(sql, new RowMapper<MoneyPO>() {
            @Override
            public MoneyPO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return result2po(rs);
            }
        });
        System.out.println("queryByRowMapper: " + result);
    }

    private void queryByPlaceHolder() {
        String sql = "select * from money where id > ? limit ?";
        // 占位方式，在最后面加上实际的sql参数，第二个参数也可以换成 ResultSetExtractor
        List<MoneyPO> result = jdbcTemplate.query(sql, new RowMapper<MoneyPO>() {
            @Override
            public MoneyPO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return result2po(rs);
            }
        }, 1, 2);
        System.out.println("queryByPlaceHolder: " + result);
    }

    private void queryByPreparedStatement() {
        // 使用 PreparedStatementCreator查询，主要是可以设置连接相关参数, 如设置为只读
        List<MoneyPO> result = jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                con.setReadOnly(true);
                PreparedStatement statement = con.prepareStatement("select * from money where id > ? limit ?");
                // 表示 id > 1
                statement.setInt(1, 1);
                // 表示 limit 2
                statement.setInt(2, 2);
                return statement;
            }
        }, new RowMapper<MoneyPO>() {
            @Override
            public MoneyPO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return result2po(rs);
            }
        });

        System.out.println("queryByPreparedStatement: " + result);
    }

    private void queryNoRecord() {
        // 没有命中的情况下，会怎样
        List<MoneyPO> result = jdbcTemplate
                .query("select * from money where id > ? limit ?", new Object[]{100, 2}, new RowMapper<MoneyPO>() {
                    @Override
                    public MoneyPO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return result2po(rs);
                    }
                });

        System.out.println("queryNoRecord: " + result);
    }

}
