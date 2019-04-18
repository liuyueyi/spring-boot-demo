package com.git.hui.boot.jdbc.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 使用姿势和update差不多
 * Created by @author yihui in 11:08 19/4/4.
 */
@Component
public class DeleteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void delete() {
        int ans = jdbcTemplate.update("delete from money where id = 13");
        System.out.println("delete: " + ans);
    }
}
