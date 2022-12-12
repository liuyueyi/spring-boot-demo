package com.git.hui.schema;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author YiHui
 * @date 2022/12/9
 */
@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List list = jdbcTemplate.queryForList("select * from user limit 2");
        log.info("启动成功，初始化数据: {}", list);
    }
}
