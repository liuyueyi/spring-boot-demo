package com.git.hui.boot.solr.solr;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;


/**
 * 演示solr中，文档修改的使用姿势
 * Created by @author yihui in 19:18 19/5/26.
 */
@Service
public class SolrUpdateDocService {
    @Autowired
    private SolrTemplate solrTemplate;

    public void update() {
        //        testAddByDoc();
        //        testAddByBean();
        //        testBatchAddByBean();
        testUpdateDoc();
    }


    public void testAddByDoc() {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 3);
        document.addField("title", "testAddByDoc!");
        document.addField("content", "通过solrTemplate新增文档");
        document.addField("type", 2);
        document.addField("create_at", System.currentTimeMillis() / 1000);
        document.addField("publish_at", System.currentTimeMillis() / 1000);

        UpdateResponse response = solrTemplate.saveDocument("yhh", document);
        solrTemplate.commit("yhh");
        System.out.println("over:" + response);
    }

    @Data
    public static class DocDO {
        @Field("id")
        private Integer id;
        @Field("title")
        private String title;
        @Field("content")
        private String content;
        @Field("type")
        private Integer type;
        @Field("create_at")
        private Long createAt;
        @Field("publish_at")
        private Long publishAt;
    }

    /**
     * 新增
     */
    private void testAddByBean() {
        DocDO docDO = new DocDO();
        docDO.setId(4);
        docDO.setTitle("addByBean");
        docDO.setContent("新增一个测试文档");
        docDO.setType(1);
        docDO.setCreateAt(System.currentTimeMillis() / 1000);
        docDO.setPublishAt(System.currentTimeMillis() / 1000);

        UpdateResponse response = solrTemplate.saveBean("yhh", docDO);
        solrTemplate.commit("yhh");
        System.out.println(response);
    }


    private void testBatchAddByBean() {
        DocDO docDO = new DocDO();
        docDO.setId(5);
        docDO.setTitle("addBatchByBean - 1");
        docDO.setContent("新增一个测试文档");
        docDO.setType(1);
        docDO.setCreateAt(System.currentTimeMillis() / 1000);
        docDO.setPublishAt(System.currentTimeMillis() / 1000);

        DocDO docDO2 = new DocDO();
        docDO2.setId(6);
        docDO2.setTitle("addBatchByBean - 2");
        docDO2.setContent("新增一个测试文档");
        docDO2.setType(1);
        docDO2.setCreateAt(System.currentTimeMillis() / 1000);
        docDO2.setPublishAt(System.currentTimeMillis() / 1000);

        UpdateResponse response = solrTemplate.saveBeans("yhh", Arrays.asList(docDO, docDO2));
        solrTemplate.commit("yhh");
        System.out.println(response);
    }

    public void testUpdateDoc() {
        DocDO docDO = new DocDO();
        docDO.setId(5);
        docDO.setTitle("修改之后!!!");
        docDO.setType(1);
        docDO.setCreateAt(System.currentTimeMillis() / 1000);
        docDO.setPublishAt(System.currentTimeMillis() / 1000);

        UpdateResponse response = solrTemplate.saveBean("yhh", docDO);
        solrTemplate.commit("yhh");
        System.out.println(response);
    }

}
