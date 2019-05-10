package com.git.hui.boot.solr;

        import com.git.hui.boot.solr.solr.SolrSearchService;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 19:44 19/5/10.
 */
@SpringBootApplication
public class Application {
    private SolrSearchService solrSearchService;

    public Application(SolrSearchService solrSearchService) {
        this.solrSearchService = solrSearchService;

        query();
    }

    private void query() {
        this.solrSearchService.query();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
