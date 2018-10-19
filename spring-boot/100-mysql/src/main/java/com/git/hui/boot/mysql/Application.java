package com.git.hui.boot.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by @author yihui in 20:54 18/9/25.
 */
@Slf4j
@SpringBootApplication
public class Application {

    public Application(JdbcTemplate jdbcTemplate) {
        log.warn("application start!!!");
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from Subscribe limit 2");
        log.info("result: {}", result);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
