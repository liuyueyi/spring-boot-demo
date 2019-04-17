package com.git.hui.boot.jdbc.update;

import com.git.hui.boot.jdbc.query.MoneyPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;

/**
 * Created by @author yihui in 11:08 19/4/4.
 */
@Component
public class UpdateService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void update() {
        updateOne();
    }


    private MoneyPO queryById(int id) {
        return jdbcTemplate.queryForObject(
                "select id, `name`, money, is_deleted as isDeleted, unix_timestamp(create_at) as " +
                        "created, unix_timestamp(update_at) as updated from money where id=?",
                new BeanPropertyRowMapper<>(MoneyPO.class), id);
    }

    private void updateOne() {
        int id = 10;

        // 最基本的sql更新
        String sql = "update money set money=money + 999 where id =" + id;
        int ans = jdbcTemplate.update(sql);
        System.out.println("basic update: " + ans + " | db: " + queryById(id));


        // 占位方式
        sql = "update money set money=money + ? where id = ?";
        ans = jdbcTemplate.update(sql, 888, id);
        System.out.println("placeholder update: " + ans + " | db: " + queryById(id));


        // 通过 PreparedStatementCreator 方式更新
        ans = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                // 设置自动提交，设置100ms的超时，这种方式最大的好处是可以控制db连接的参数
                try {
                    connection.setAutoCommit(true);
                    connection.setNetworkTimeout(Executors.newSingleThreadExecutor(), 10);
                    PreparedStatement statement =
                            connection.prepareStatement("update money set money=money + ? where id " + "= ?");
                    statement.setInt(1, 777);
                    statement.setInt(2, id);
                    return statement;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
        System.out.println("statementCreator update: " + ans + " | db: " + queryById(id));


        // 通过 PreparedStatementSetter 来设置占位参数值
        ans = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, 666);
                preparedStatement.setInt(2, id);
            }
        });
        System.out.println("statementSetter update: " + ans + " | db: " + queryById(id));
    }
}
