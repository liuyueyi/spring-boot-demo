package com.git.hui.boot.mongo;

import com.git.hui.boot.mongo.wrapper.MongoReadWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 19:30 18/12/26.
 */
@SpringBootApplication
public class Application {

    private final MongoReadWrapper mongoReadWrapper;

    public Application(MongoReadWrapper mongoReadWrapper) {
        this.mongoReadWrapper = mongoReadWrapper;

        // 指定查询
        this.mongoReadWrapper.specialFieldQuery();

        // 多条件查询
        this.mongoReadWrapper.andQuery();

        this.mongoReadWrapper.orQuery();

        this.mongoReadWrapper.inQuery();

        this.mongoReadWrapper.compareBigQuery();

        this.mongoReadWrapper.compareSmallQuery();

        this.mongoReadWrapper.regexQuery();

        this.mongoReadWrapper.countQuery();

        this.mongoReadWrapper.groupQuery();

        this.mongoReadWrapper.sortQuery();

        this.mongoReadWrapper.pageQuery();

        this.mongoReadWrapper.complexQuery();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
