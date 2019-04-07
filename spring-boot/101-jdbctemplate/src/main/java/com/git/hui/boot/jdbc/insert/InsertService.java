package com.git.hui.boot.jdbc.insert;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 测试表为 money， 结构如下
 *
 * CREATE TABLE `money` (
 * `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 * `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
 * `money` int(26) NOT NULL DEFAULT '0' COMMENT '钱',
 * `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
 * `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 * `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 * PRIMARY KEY (`id`),
 * KEY `name` (`name`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
 *
 * Created by @author yihui in 11:08 19/4/4.
 */
@Component
public class InsertService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 简单的新增一条数据
     */
    public void basicInsert() {
        System.out.println("basic insert: " + insertBySql());
        System.out.println("insertBySqlParams: " + insertBySqlParams());
        System.out.println("insertByStatement: " + insertByStatement());
        System.out.println("insertByStatement2: " + insertByStatement2());
        System.out.println("insertAndReturn: " + insertAndReturnId());

        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from money");
        System.out.println("after insert, the records:\n" + result);
    }

    private boolean insertBySql() {
        // 简单的sql执行
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES ('一灰灰blog', 100, 0);";
        return jdbcTemplate.update(sql) > 0;
    }

    private boolean insertBySqlParams() {
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES (?, ?, ?);";
        return jdbcTemplate.update(sql, "一灰灰2", 200, 0) > 0;
    }

    private boolean insertByStatement() {
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES (?, ?, ?);";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, "一灰灰3");
                preparedStatement.setInt(2, 300);
                byte b = 0;
                preparedStatement.setByte(3, b);
            }
        }) > 0;
    }

    private boolean insertByStatement2() {
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES (?, ?, ?);";
        return jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "一灰灰4");
                preparedStatement.setInt(2, 400);
                byte b = 0;
                preparedStatement.setByte(3, b);
                return preparedStatement;
            }
        }) > 0;
    }

    /**
     * 新增数据，并返回主键id
     *
     * @return
     */
    private int insertAndReturnId() {
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                // 指定主键
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                preparedStatement.setString(1, "一灰灰5");
                preparedStatement.setInt(2, 500);
                byte b = 0;
                preparedStatement.setByte(3, b);
                return preparedStatement;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 批量插入数据
     */
    public void batchInsert() {
        batchInsertBySql();
        batchInsertByParams();
        batchInsertByStatement();
        batchInsertAndReturnId();
    }

    private void batchInsertBySql() {
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES " +
                "('Batch 一灰灰blog', 100, 0), ('Batch 一灰灰blog 2', 100, 0);";
        int[] ans = jdbcTemplate.batchUpdate(sql);
        System.out.println("batch insert by sql: " + JSON.toJSONString(ans));
    }

    private void batchInsertByParams() {
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES (?, ?, ?);";

        Object[] param1 = new Object[]{"Batch 一灰灰 3", 200, 0};
        Object[] param2 = new Object[]{"Batch 一灰灰 4", 200, 0};
        int[] ans = jdbcTemplate.batchUpdate(sql, Arrays.asList(param1, param2));
        System.out.println("batch insert by params: " + JSON.toJSONString(ans));
    }

    private void batchInsertByStatement() {
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES (?, ?, ?);";

        int[] ans = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                if (i == 0) {
                    preparedStatement.setString(1, "batch 一灰灰5");
                } else {
                    preparedStatement.setString(1, "batch 一灰灰6");
                }
                preparedStatement.setInt(2, 300);
                byte b = 0;
                preparedStatement.setByte(3, b);
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        });
        System.out.println("batch insert by statement: " + JSON.toJSONString(ans));
    }

    @Autowired
    private ExtendJdbcTemplate extendJdbcTemplate;

    private void batchInsertAndReturnId() {
        String sql = "INSERT INTO `money` (`name`, `money`, `is_deleted`) VALUES (?, ?, ?);";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        extendJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                if (i == 0) {
                    preparedStatement.setString(1, "batch 一灰灰7");
                } else {
                    preparedStatement.setString(1, "batch 一灰灰8");
                }
                preparedStatement.setInt(2, 400);
                byte b = 0;
                preparedStatement.setByte(3, b);
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        }, generatedKeyHolder);

        System.out.println("batch insert and return id ");
        List<Map<String, Object>> objectMap = generatedKeyHolder.getKeyList();
        for (Map<String, Object> map : objectMap) {
            System.out.println(map.get("GENERATED_KEY"));
        }
    }
}
