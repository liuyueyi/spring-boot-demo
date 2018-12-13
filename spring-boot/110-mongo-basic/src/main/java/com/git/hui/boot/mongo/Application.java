package com.git.hui.boot.mongo;

import com.git.hui.boot.mongo.context.MongoTemplateHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 19:19 18/12/13.
 */
@SpringBootApplication
public class Application {

    private static final String COLLECTION_NAME = "personal_info";


    public Application(MongoTemplateHelper mongoTemplateHelper) {
        Map<String, Object> records = new HashMap<>(4);
        records.put("name", "小灰灰Blog");
        records.put("github", "https://github.com/liuyueyi");
        records.put("time", LocalDateTime.now());

        mongoTemplateHelper.saveRecord(records, COLLECTION_NAME);

        Map<String, Object> query = new HashMap<>(4);
        query.put("name", "小灰灰Blog");
        mongoTemplateHelper.queryRecord(query, COLLECTION_NAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
