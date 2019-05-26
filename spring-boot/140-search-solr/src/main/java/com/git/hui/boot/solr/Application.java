package com.git.hui.boot.solr;

import com.git.hui.boot.solr.solr.SolrSearchService;
import com.git.hui.boot.solr.solr.SolrUpdateDocService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 19:44 19/5/10.
 */
@SpringBootApplication
public class Application {
    private SolrSearchService solrSearchService;
    private SolrUpdateDocService solrUpdateDocService;

    public Application(SolrSearchService solrSearchService, SolrUpdateDocService solrUpdateDocService) {
        this.solrSearchService = solrSearchService;
        this.solrUpdateDocService = solrUpdateDocService;

        //        query();
        update();
    }

    private void query() {
        this.solrSearchService.query();
    }

    private void update() {
        this.solrUpdateDocService.update();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
