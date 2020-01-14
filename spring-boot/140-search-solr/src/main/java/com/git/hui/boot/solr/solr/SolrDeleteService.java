package com.git.hui.boot.solr.solr;

import com.git.hui.boot.solr.entity.DocDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by @author yihui in 17:37 20/1/11.
 */
@Component
public class SolrDeleteService {
    @Autowired
    private SolrTemplate solrTemplate;

    public void delete() {
        printAll("init");
        this.deleteById();
        this.deleteByQuery();
        printAll("afterDelete");
    }

    private void printAll(String tag) {
        System.out.println("\n---------> query all " + tag + " start <------------\n");
        List<DocDO> list = solrTemplate
                .query("yhh", new SimpleQuery("*:*").addSort(Sort.by("content_id").ascending()), DocDO.class)
                .getContent();
        list.forEach(System.out::println);
        System.out.println("\n---------> query all " + tag + " over <------------\n");
    }

    private void deleteById() {
        solrTemplate.deleteByIds("yhh", Arrays.asList("4"));
        solrTemplate.commit("yhh");
    }

    private void deleteByQuery() {
        SolrDataQuery query = new SimpleQuery();
        query.addCriteria(Criteria.where("content").startsWith("新增"));
        solrTemplate.delete("yhh", query);
        solrTemplate.commit("yhh");
    }
}
