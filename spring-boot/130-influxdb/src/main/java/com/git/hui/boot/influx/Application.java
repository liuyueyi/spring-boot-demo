package com.git.hui.boot.influx;

import com.git.hui.boot.influx.delete.DeleteService;
import com.git.hui.boot.influx.insert.InsertService;
import com.git.hui.boot.influx.query.QueryService;
import com.git.hui.boot.influx.update.UpdateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 16:49 19/4/30.
 */
@SpringBootApplication
public class Application {

    private InsertService insertService;
    private QueryService queryService;
    private UpdateService updateService;
    private DeleteService deleteService;

    public Application(InsertService insertService, QueryService queryService, UpdateService updateService,
            DeleteService deleteService) {
        this.insertService = insertService;
        this.queryService = queryService;
        this.updateService = updateService;
        this.deleteService = deleteService;

        insert();
        //        query();
        //        update();
        //        delete();
    }

    private void insert() {
        insertService.time();
        //        insertService.add();
        //        insertService.batchAdd();
    }

    private void query() {
        queryService.query();
    }

    private void update() {
        updateService.update();
    }

    private void delete() {
        deleteService.delete();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
