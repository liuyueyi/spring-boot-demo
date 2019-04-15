package com.git.hui.boot.jdbc;

import com.git.hui.boot.jdbc.insert.InsertService;
import com.git.hui.boot.jdbc.query.QueryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 11:04 19/4/4.
 */
@SpringBootApplication
public class Application {

    private InsertService insertService;
    private QueryService queryService;

    public Application(InsertService insertService, QueryService queryService) {
        this.insertService = insertService;
        this.queryService = queryService;

        queryTest();
    }

    public void insertTest() {
        insertService.basicInsert();
        insertService.batchInsert();
    }

    public void queryTest() {
        queryService.queryForMap();
        queryService.queryForObject();
        queryService.queryForList();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
