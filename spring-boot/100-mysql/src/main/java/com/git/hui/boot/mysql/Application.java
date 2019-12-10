package com.git.hui.boot.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by @author yihui in 20:54 18/9/25.
 */
@Slf4j
@SpringBootApplication
public class Application {

    public Application(JdbcTemplate jdbcTemplate) {
        log.warn("application start!!!");

        // 插入emoj 表情
        jdbcTemplate.update("insert into Subscribe (`email`, `nick`) values (?, ?)",
                UUID.randomUUID().toString() + "@t.com", "🐺狼");

        List<Map<String, Object>> r = jdbcTemplate.queryForList("select * from Subscribe order by id desc limit 1");
        log.info("r: {}", r);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
