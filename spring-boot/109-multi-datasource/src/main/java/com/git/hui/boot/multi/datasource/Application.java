package com.git.hui.boot.multi.datasource;

import com.git.hui.boot.multi.datasource.server.JdbcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 20/12/27
 */
@SpringBootApplication
public class Application {

    public Application(JdbcServer jdbcServer) {
        jdbcServer.query();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
