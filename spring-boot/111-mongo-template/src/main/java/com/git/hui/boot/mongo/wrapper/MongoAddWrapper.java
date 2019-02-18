package com.git.hui.boot.mongo.wrapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by @author yihui in 22:28 19/1/23.
 */
@Component
public class MongoAddWrapper {
    private static final String COLLECTION_NAME = "demo";

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增一条记录
     */
    public void insert() {
        JSONObject object = new JSONObject();
        object.put("name", "一灰灰blog");
        object.put("desc", "欢迎关注一灰灰Blog");
        object.put("age", 28);

        // 插入一条document
        mongoTemplate.insert(object, COLLECTION_NAME);


        JSONObject ans = mongoTemplate
                .findOne(new Query(Criteria.where("name").is("一灰灰blog").and("age").is(28)), JSONObject.class,
                        COLLECTION_NAME);
        System.out.println(ans);
    }

    /**
     * 批量插入
     */
    public void insertMany() {
        List<Map<String, Object>> records = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> record = new HashMap<>(4);
            record.put("wechart", "一灰灰blog");
            record.put("blog", Arrays.asList("http://spring.hhui.top", "http://blog.hhui.top"));
            record.put("nums", 210);
            record.put("t_id", i);
            records.add(record);
        }

        // 批量插入文档
        mongoTemplate.insert(records, COLLECTION_NAME);

        // 查询插入的内容
        List<Map> result =
                mongoTemplate.find(new Query(Criteria.where("wechart").is("一灰灰blog")), Map.class, COLLECTION_NAME);
        System.out.println("Query Insert Records: " + result);
    }


    /**
     * 数据不存在，通过 upsert 新插入一条数据
     *
     * set 表示修改key对应的value
     * addToSet 表示在数组中新增一条
     */
    public void upsertNoMatch() {
        // addToSet 表示将数据塞入document的一个数组成员中
        UpdateResult upResult = mongoTemplate.upsert(new Query(Criteria.where("name").is("一灰灰blog").and("age").is(100)),
                new Update().set("age", 120).addToSet("add", "额外增加"), COLLECTION_NAME);
        System.out.println("nomatch upsert return: " + upResult);

        List<JSONObject> re = mongoTemplate
                .find(new Query(Criteria.where("name").is("一灰灰blog").and("age").is(120)), JSONObject.class,
                        COLLECTION_NAME);
        System.out.println("after upsert return should not be null: " + re);
        System.out.println("------------------------------------------");
    }

    /**
     * 只有一条数据匹配，upsert 即表示更新
     */
    public void upsertOneMatch() {
        // 数据存在，使用更新
        UpdateResult result = mongoTemplate.upsert(new Query(Criteria.where("name").is("一灰灰blog").and("age").is(120)),
                new Update().set("age", 100), COLLECTION_NAME);
        System.out.println("one match upsert return: " + result);

        List<JSONObject> ans = mongoTemplate
                .find(new Query(Criteria.where("name").is("一灰灰blog").and("age").is(100)), JSONObject.class,
                        COLLECTION_NAME);
        System.out.println("after update return should be null: " + ans);
        System.out.println("------------------------------------------");
    }

    /**
     * 两条数据匹配时，upsert 将只会更新一条数据
     */
    public void upsertTwoMatch() {
        // 多条数据满足条件时，只会修改一条数据
        System.out.println("------------------------------------------");
        List<JSONObject> re = mongoTemplate
                .find(new Query(Criteria.where("name").is("一灰灰blog").and("age").in(Arrays.asList(28, 100))),
                        JSONObject.class, COLLECTION_NAME);
        System.out.println("original record: " + re);

        UpdateResult result = mongoTemplate
                .upsert(new Query(Criteria.where("name").is("一灰灰blog").and("age").in(Arrays.asList(28, 100))),
                        new Update().set("age", 120), COLLECTION_NAME);
        System.out.println("two match upsert return: " + result);

        re = mongoTemplate.find(new Query(Criteria.where("name").is("一灰灰blog").and("age").is(120)), JSONObject.class,
                COLLECTION_NAME);
        System.out.println("after upsert return size should be 1: " + re);
        System.out.println("------------------------------------------");
    }

}
