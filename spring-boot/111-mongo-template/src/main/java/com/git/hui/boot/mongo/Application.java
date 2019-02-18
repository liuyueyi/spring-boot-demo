package com.git.hui.boot.mongo;

import com.git.hui.boot.mongo.wrapper.MongoReadWrapper;
import com.git.hui.boot.mongo.wrapper.MongoAddWrapper;
import com.git.hui.boot.mongo.wrapper.MongoUpdateWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 19:30 18/12/26.
 */
@SpringBootApplication
public class Application {

    private final MongoReadWrapper mongoReadWrapper;
    private final MongoAddWrapper mongoAddWrapper;
    private final MongoUpdateWrapper mongoUpdateWrapper;

    public Application(MongoReadWrapper mongoReadWrapper, MongoAddWrapper mongoAddWrapper,
            MongoUpdateWrapper mongoUpdateWrapper) {
        this.mongoReadWrapper = mongoReadWrapper;
        this.mongoAddWrapper = mongoAddWrapper;
        this.mongoUpdateWrapper = mongoUpdateWrapper;

        updateDemo();
    }

    private void queryDemo() {
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

    private void addDemo() {
        this.mongoAddWrapper.insert();
        this.mongoAddWrapper.insertMany();
        this.mongoAddWrapper.upsertNoMatch();
        this.mongoAddWrapper.upsertOneMatch();
        this.mongoAddWrapper.upsertTwoMatch();
    }

    private void updateDemo() {
        this.mongoUpdateWrapper.basicUpdate();
        this.mongoUpdateWrapper.fieldUpdate();
        this.mongoUpdateWrapper.updateInnerArray();
        this.mongoUpdateWrapper.updateInnerDoc();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
