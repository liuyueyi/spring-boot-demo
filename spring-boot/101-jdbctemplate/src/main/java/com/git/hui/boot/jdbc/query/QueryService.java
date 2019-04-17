package com.git.hui.boot.jdbc.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 查询相关操作姿势
 * Created by @author yihui in 11:07 19/4/4.
 */
@Component
public class QueryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 直接根据sql进行查询，将结果封装到Map容器中， key为列名，value为值
     */
    public void queryForMap() {
        // 直接写sql方式，容易有sql注入的风险，如id = 1 的value是外部传入的，如果传入的是一个字符串 "1 or 1=1"，这样就实现了一个典型的sql注入
        String sql = "select * from money where id =1";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        System.out.println("QueryForMap by direct sql ans: " + map);


        // 使用占位符替换方式查询
        sql = "select * from money where id=?";
        map = jdbcTemplate.queryForMap(sql, new Object[]{1});
        System.out.println("QueryForMap by ? ans: " + map);


        // 指定传参类型, 通过传参来填充sql中的占位
        sql = "select * from money where id =?";
        map = jdbcTemplate.queryForMap(sql, 1);
        System.out.println("QueryForMap by ? ans: " + map);


        // 查不到数据的情况
        try {
            sql = "select * from money where id =?";
            map = jdbcTemplate.queryForMap(sql, 100);
            System.out.println("QueryForMap by ? ans: " + map);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }

    public void queryForObject() {
        System.out.println("============ query for object! ==============");

        // sql + 指定返回类型方式访问
        // 使用这种sql的有点就是方便使用反射方式，实现PO的赋值
        String sql =
                "select id, `name`, money, is_deleted as isDeleted, unix_timestamp(create_at) as created, unix_timestamp(update_at) as updated from money limit 1;";
        // 需要注意，下标以1开始
        MoneyPO moneyPO = jdbcTemplate.queryForObject(sql, new RowMapper<MoneyPO>() {
            @Override
            public MoneyPO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MoneyPO po = new MoneyPO();
                po.setId(rs.getInt(1));
                po.setName(rs.getString(2));
                po.setMoney(rs.getInt(3));
                po.setDeleted(rs.getBoolean(4));
                po.setCreated(rs.getLong(5));
                po.setUpdated(rs.getLong(6));
                return po;
            }
        });
        System.out.println("queryFroObject by RowMapper: " + moneyPO);


        // 直接使用columnName来获取对应的值，这里就可以考虑使用反射方式来赋值，减少getter/setter
        moneyPO = jdbcTemplate.queryForObject(sql, new RowMapper<MoneyPO>() {
            @Override
            public MoneyPO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MoneyPO po = new MoneyPO();
                po.setId(rs.getInt("id"));
                po.setName(rs.getString("name"));
                po.setMoney(rs.getInt("money"));
                po.setDeleted(rs.getBoolean("isDeleted"));
                po.setCreated(rs.getLong("created"));
                po.setUpdated(rs.getLong("updated"));
                return po;
            }
        });
        System.out.println("queryFroObject by RowMapper: " + moneyPO);


        // 更简单的方式，直接通过BeanPropertyRowMapper来实现属性的赋值，前提是sql返回的列名能正确匹配
        moneyPO = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MoneyPO.class));
        System.out.println("queryForObject by BeanPropertyRowMapper: " + moneyPO);


        // 下面开始测试下 org.springframework.jdbc.core.JdbcTemplate.queryForObject(java.lang.String, java.lang.Class<T>, java.lang.Object...)
        // 根据测试，这个类型，只能是基本类型
        String sql2 = "select id from money where id=?";
        Integer res = jdbcTemplate.queryForObject(sql2, Integer.class, 1);
        System.out.println("queryForObject by requireId return: " + res);

        try {
            // 只允许返回一个数据，然后传入基本数据类型，下面这种方式是禁止的
            MoneyPO po = jdbcTemplate.queryForObject(sql, MoneyPO.class);
            System.out.println("queryForObject by requireType return: " + po);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            // 如果没有命中，则抛异常出来
            Integer notHit = jdbcTemplate.queryForObject(sql2, Integer.class, 100);
            System.out.println("not hit: " + notHit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("============ query for object! ==============");
    }

    public void queryForList() {
        System.out.println("============ query for List! ==============");
        String sql =
                "select id, `name`, money, is_deleted as isDeleted, unix_timestamp(create_at) as created, unix_timestamp(update_at) as updated from money limit 3;";

        // 默认返回 List<Map<String, Object>> 类型数据，如果一条数据都没有，则返回一个空的集合
        List<Map<String, Object>> res = jdbcTemplate.queryForList(sql);
        System.out.println("basicQueryForList: " + res);

        String sql2 = "select id, `name`, money, is_deleted as isDeleted, unix_timestamp(create_at) as created, " +
                "unix_timestamp(update_at) as updated from money where id=? or name=?;";
        res = jdbcTemplate.queryForList(sql2, 2, "一灰灰2");
        System.out.println("queryForList by template: " + res);

        System.out.println("============ query for List! ==============");
    }
}
