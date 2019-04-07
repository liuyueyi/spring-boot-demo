package com.git.hui.boot.jdbc.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 查询相关操作姿势
 * Created by @author yihui in 11:07 19/4/4.
 */
@Component
public class QueryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

}
