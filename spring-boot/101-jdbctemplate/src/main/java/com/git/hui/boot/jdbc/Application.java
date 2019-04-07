package com.git.hui.boot.jdbc;

import com.git.hui.boot.jdbc.insert.InsertService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 11:04 19/4/4.
 */
@SpringBootApplication
public class Application {

    public Application(InsertService insertService) {
        insertService.basicInsert();
        insertService.batchInsert();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
