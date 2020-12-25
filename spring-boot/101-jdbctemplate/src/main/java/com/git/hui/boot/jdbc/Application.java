package com.git.hui.boot.jdbc;

import com.git.hui.boot.jdbc.insert.InsertService;
import com.git.hui.boot.jdbc.query.QueryService;
import com.git.hui.boot.jdbc.query.QueryServiceV2;
import com.git.hui.boot.jdbc.update.UpdateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 11:04 19/4/4.
 */
@SpringBootApplication
public class Application {

    private InsertService insertService;
    private QueryService queryService;
    private QueryServiceV2 queryServiceV2;
    private UpdateService updateService;

    public Application(InsertService insertService, QueryService queryService, QueryServiceV2 queryServiceV2,
                       UpdateService updateService) {
        this.insertService = insertService;
        this.queryService = queryService;
        this.queryServiceV2 = queryServiceV2;
        this.updateService = updateService;

        insertTest();
        queryTest();
        queryTest2();
        updateTest();
    }


    public void insertTest() {
        // 第一个调用
        insertService.basicInsert();
        insertService.batchInsert();
    }

    public void queryTest() {
        // 第二个调用
        queryService.queryForMap();
        queryService.queryForObject();
        queryService.queryForList();
    }

    public void queryTest2() {
        // 第三个调用
        queryServiceV2.queryForRowSet();
        queryServiceV2.query();
    }

    public void updateTest() {
        // 最后调用
        updateService.update();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
