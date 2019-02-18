package com.git.hui.boot.mongo.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by @author yihui in 14:14 19/1/24.
 */
@Component
public class MongoUpdateWrapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "demo";

    private void queryAndPrint(Query query, String tag) {
        System.out.println("------------- after " + tag + " age -------------");
        Map record = mongoTemplate.findOne(query, Map.class, COLLECTION_NAME);
        System.out.println("query records: " + record);
        System.out.println("-------------  end " + tag + " age --------------\n");

    }

    /**
     * 基本更新操作，根据某一条满足条件的数据
     *
     * 原始文档内容为：
     */
    public void basicUpdate() {
        /*
         * ﻿{
         *     "_id" : ObjectId("5c49b07ce6652f7e1add1ea2"),
         *     "age" : 100,
         *     "name" : "一灰灰blog",
         *     "desc" : "Java Developer",
         *     "add" : [
         *         "额外增加"
         *     ],
         *     "date" : ISODate("2019-01-28T08:00:08.373Z"),
         *     "doc" : {
         *         "key" : "小目标",
         *         "value" : "升职加薪，迎娶白富美"
         *     }
         * }
         */

        // 1. 直接修改值的内容
        Query query = new Query(Criteria.where("_id").is("5c49b07ce6652f7e1add1ea2"));

        Update update = new Update().set("desc", "Java & Python Developer");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "set");


        // 数字修改，实现添加or减少
        Update numUp = new Update().inc("age", 20L);
        mongoTemplate.updateFirst(query, numUp, COLLECTION_NAME);
        queryAndPrint(query, "inc");


        // 数字比较修改
        Update cmpUp = new Update().max("age", 88);
        mongoTemplate.updateFirst(query, cmpUp, COLLECTION_NAME);
        queryAndPrint(query, "cmp");


        // 乘法
        Update mulUp = new Update().multiply("age", 3);
        mongoTemplate.updateFirst(query, mulUp, COLLECTION_NAME);
        queryAndPrint(query, "multiply");


        // 日期修改
        Update dateUp = new Update().currentDate("date");
        mongoTemplate.updateFirst(query, dateUp, COLLECTION_NAME);
        queryAndPrint(query, "date");
    }

    /**
     * 修改字段名，新增字段，删除字段
     */
    public void fieldUpdate() {
        /**
         * ﻿{
         *     "_id" : ObjectId("5c6a7ada10ffc647d301dd62"),
         *     "age" : 28.0,
         *     "name" : "一灰灰blog",
         *     "desc" : "Java Developer",
         *     "add" : [
         *         "额外增加"
         *     ],
         *     "date" : ISODate("2019-01-28T08:00:08.373Z"),
         *     "doc" : {
         *         "key" : "小目标",
         *         "value" : "升职加薪，迎娶白富美"
         *     }
         * }
         */
        Query query = new Query(Criteria.where("_id").is("5c6a7ada10ffc647d301dd62"));
        renameFiled(query);

        addField(query);
        delField(query);
    }

    private void renameFiled(Query query) {
        Update update = new Update().rename("desc", "skill");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);

        queryAndPrint(query, "rename");

        // 如果字段不存在，相当于没有更新s
        update = new Update().rename("desc", "s-skill");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "rename Not exists!");
    }

    private void addField(Query query) {
        // 新增一个字段
        // 直接使用set即可
        Update update = new Update().set("new-skill", "Python");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);

        queryAndPrint(query, "addField");

        // 当更新一个不存在的文档时，可以使用setOnInsert
        // 如果要更新的文档存在那么$setOnInsert操作符不做任何处理；
    }

    private void delField(Query query) {
        // 删除字段，如果不存在，则不操作
        Update update = new Update().unset("new-skill");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);

        queryAndPrint(query, "delField");
    }

    /**
     * 更新文档中字段为数组成员的值
     */
    public void updateInnerArray() {
        /**
         * ﻿{
         *     "_id" : ObjectId("5c6a7ada10ffc647d301dd62"),
         *     "age" : 28.0,
         *     "name" : "一灰灰blog",
         *     "skill" : "Java Developer",
         *     "add" : [
         *         "额外增加"
         *     ],
         *     "date" : ISODate("2019-01-28T08:00:08.373Z"),
         *     "doc" : {
         *         "key" : "小目标",
         *         "value" : "升职加薪，迎娶白富美"
         *     }
         * }
         */
        Query query = new Query(Criteria.where("_id").is("5c6a7ada10ffc647d301dd62"));
        this.addData2Array(query);
        this.batchAddData2Array(query);
        this.delArrayData(query);
        this.updateArrayData(query);
    }

    private void addData2Array(Query query) {
        // 新加一个元素到数组，如果已经存在，则不会加入
        String insert = "新添加>>" + System.currentTimeMillis();
        Update update = new Update().addToSet("add", insert);
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "add2List");

        // push 新增元素，允许出现重复的数据
        update = new Update().push("add", 10);
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "push2List");
    }

    private void batchAddData2Array(Query query) {
        // 批量插入数据到数组中, 注意不会将重复的数据丢入mongo数组中
        Update update = new Update().addToSet("add").each("2", "2", "3");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "batchAdd2List");

//        // push 批量插入到数组中，允许重复
//        update = new Update().pushAll("add", new Object[]{100, 101, 102, 102, 101});
//        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
//        queryAndPrint(query, "batchPush2List");
    }

    private void delArrayData(Query query) {
        // 删除数组中元素
        Update update = new Update().pull("add", "2");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "delArrayData");
    }

    private void updateArrayData(Query query) {
        // 使用set，field.index 来更新数组中的值
        // 更新数组中的元素，如果元素存在，则直接更新；如果数组个数小于待更新的索引位置，则前面补null
        Update update = new Update().set("add.1", "updateField");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "updateListData");

        update = new Update().set("add.10", "nullBefore");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "updateListData");
    }

    /**
     * 更新文档中字段为document类型的值
     */
    public void updateInnerDoc() {
        /**
         * ﻿{
         *     "_id" : ObjectId("5c6a956b10ffc647d301dd63"),
         *     "age" : 18.0,
         *     "name" : "一灰灰blog",
         *     "date" : ISODate("2019-02-28T08:00:08.373Z"),
         *     "doc" : {
         *         "key" : "小目标",
         *         "value" : "升职加薪，迎娶白富美"
         *     },
         *     "skill" : "Java Developer"
         * }
         */

        Query query = new Query(Criteria.where("_id").is("5c6a956b10ffc647d301dd63"));
        this.addFieldToDoc(query);
        this.updateFieldOfDoc(query);
        this.delFieldOfDoc(query);
    }

    private void addFieldToDoc(Query query) {
        // 内嵌doc新增field
        Update update = new Update().set("doc.title", "好好学习，天天向上!");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "addFieldToDoc");
    }

    private void updateFieldOfDoc(Query query) {
        // 内嵌doc修改field
        Update update = new Update().set("doc.title", "新的标题：一灰灰Blog!");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "updateFieldOfDoc");
    }

    private void delFieldOfDoc(Query query) {
        // 删除内嵌doc中的field
        Update update = new Update().unset("doc.title");
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        queryAndPrint(query, "delFieldOfDoc");
    }
}
