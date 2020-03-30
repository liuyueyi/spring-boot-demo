package com.git.hui.boot.solr.bean;

import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Created by @author yihui in 19:49 20/3/29.
 */
@Component
public class SolrOperater {

    @Autowired
    private SolrTemplate solrTemplate;


    public void operate() {
        testAddByDoc();
        queryById();
    }

    public void testAddByDoc() {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 999999);
        document.addField("content_id", 3);
        document.addField("title", "testAddByDoc!");
        document.addField("content", "新增哒哒哒");
        document.addField("type", 2);
        document.addField("create_at", System.currentTimeMillis() / 1000);
        document.addField("publish_at", System.currentTimeMillis() / 1000);
        UpdateResponse response = solrTemplate.saveDocument("yhh", document, Duration.ZERO);
        solrTemplate.commit("yhh");
        System.out.println("over:" + response);
    }

    private void queryById() {
        DocDO ans = solrTemplate.getById("yhh", 999999, DocDO.class).get();
        System.out.println("queryById: " + ans);
    }
}
