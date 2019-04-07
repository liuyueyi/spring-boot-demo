package com.git.hui.boot.jdbc.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 11:08 19/4/4.
 */
@Component
public class UpdateService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
