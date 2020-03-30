package com.git.hui.boot.solr;

import com.git.hui.boot.solr.bean.SolrOperater;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 19:48 20/3/29.
 */
@SpringBootApplication
public class Application {

    public Application(SolrOperater solrOperater) {
        solrOperater.operate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
