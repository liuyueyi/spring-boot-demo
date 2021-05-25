package com.git.hui.boot.jdbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yihui
 * @date 21/5/24
 */
@Service
public class TransApiImpl implements TransApi {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean update(int id) {
        String sql = "replace into money (id, name, money) values (" + id + ", '事务测试', 200)";
        jdbcTemplate.execute(sql);

        Object ans = jdbcTemplate.queryForMap("select * from money where id = 111");
        System.out.println(ans);

        throw new RuntimeException("事务回滚");
    }
}
