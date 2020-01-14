package com.git.hui.boot.solr;

import com.git.hui.boot.solr.solr.SolrDeleteService;
import com.git.hui.boot.solr.solr.SolrSearchService;
import com.git.hui.boot.solr.solr.SolrUpdateDocService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 19:44 19/5/10.
 */
@SpringBootApplication
public class Application {

    public Application(SolrSearchService solrSearchService, SolrUpdateDocService solrUpdateDocService,
            SolrDeleteService solrDeleteService) {
        solrUpdateDocService.update();
        solrSearchService.query();
//        solrDeleteService.delete();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
