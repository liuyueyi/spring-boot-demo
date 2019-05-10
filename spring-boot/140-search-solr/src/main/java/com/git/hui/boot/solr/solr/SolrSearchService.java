package com.git.hui.boot.solr.solr;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;


/**
 * Created by @author yihui in 19:45 19/5/10.
 */
@Component
public class SolrSearchService {
    @Autowired
    private SolrTemplate solrTemplate;

    @Data
    public static class DocDO {
        private Integer id;
        private String title;
        private String content;
        private Integer type;
        private Long create_at;
        private Long publish_at;
    }

    public void query() {
        DocDO ans = solrTemplate.getById("yhh", 2, DocDO.class).get();
        System.out.println(ans);
    }

}
