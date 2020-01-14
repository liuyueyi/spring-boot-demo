package com.git.hui.boot.solr.solr;

import com.git.hui.boot.solr.entity.DocDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Created by @author yihui in 19:45 19/5/10.
 */
@Component
public class SolrSearchService {
    @Autowired
    private SolrTemplate solrTemplate;

    public void query() {
        System.out.println("\n----------- queryService start -------------\n");
        this.queryById();
        this.simpleQuery();
        this.querySpecialFiled();
        this.queryRange();
        this.queryAndSort();
        this.queryPageSize();
        this.queryGroup();
        System.out.println("\n----------- queryService over -------------\n");
    }


    private void queryById() {
        DocDO ans = solrTemplate.getById("yhh", 1, DocDO.class).get();
        System.out.println("queryById: " + ans);

        Collection<DocDO> list = solrTemplate.getByIds("yhh", Arrays.asList(1, 2), DocDO.class);
        System.out.println("queryByIds: " + list);
    }

    /**
     * 简单查询
     */
    private void simpleQuery() {
        Query query = new SimpleQuery("title:一灰灰");
        Page<DocDO> ans = solrTemplate.query("yhh", query, DocDO.class);
        System.out.println("simpleQuery : " + ans.getContent());

        query = new SimpleQuery();
        query.addCriteria(new Criteria("content").contains("一灰灰"));

        ans = solrTemplate.query("yhh", query, DocDO.class);
        System.out.println("simpleQuery : " + ans.getContent());

        // fq查询
        query = new SimpleQuery("content: *一灰灰*");
        query.addFilterQuery(FilterQuery.filter(Criteria.where("title").contains("blog")));
        ans = solrTemplate.query("yhh", query, DocDO.class);
        System.out.println("simpleQueryAndFilter: " + ans.getContent());


        // 多个查询条件
        query = new SimpleQuery();
        query.addCriteria(Criteria.where("title").contains("一灰灰").and("content_id").lessThan(2));
        ans = solrTemplate.query("yhh", query, DocDO.class);
        System.out.println("multiQuery: " + ans.getContent());
    }

    /**
     * 查询指定的字段
     */
    private void querySpecialFiled() {
        SimpleQuery query = new SimpleQuery();
        query.addCriteria(Criteria.where("content_id").lessThanEqual(2));
        // fl 查询
        query.addProjectionOnFields("id", "title", "content");

        List<DocDO> ans = solrTemplate.query("yhh", query, DocDO.class).getContent();
        System.out.println("querySpecialField: " + ans);
    }

    /**
     * 范围查询
     */
    private void queryRange() {
        Query query = new SimpleQuery();
        query.addCriteria(Criteria.where("content_id").between(1, 3));
        query.addSort(Sort.by("content_id").ascending());
        List<DocDO> ans = solrTemplate.query("yhh", query, DocDO.class).getContent();
        System.out.println("queryRange: " + ans);

        query = new SimpleQuery();
        query.addCriteria(Criteria.where("content_id").between(1, 3, false, false));
        query.addSort(Sort.by("content_id").ascending());
        ans = solrTemplate.query("yhh", query, DocDO.class).getContent();
        System.out.println("queryRange: " + ans);
    }

    /**
     * 查询并排序
     */
    private void queryAndSort() {
        // 排序
        Query query = new SimpleQuery();
        query.addCriteria(new Criteria("content").contains("一灰灰"));
        // 倒排
        query.addSort(Sort.by("content_id").descending());
        Page<DocDO> ans = solrTemplate.query("yhh", query, DocDO.class);
        System.out.println("queryAndSort: " + ans.getContent());
    }

    /**
     * 分页
     */
    private void queryPageSize() {
        Query query = new SimpleQuery("*:*");
        query.addSort(Sort.by("content_id").ascending());
        // 指定偏移量，从0开始
        query.setOffset(2L);
        // 查询的size数量
        query.setRows(2);
        Page<DocDO> ans = solrTemplate.queryForPage("yhh", query, DocDO.class);

        // 文档数量
        long totalDocNum = ans.getTotalElements();
        List<DocDO> docList = ans.getContent();
        System.out.println("queryPageSize:  totalDocNum=" + totalDocNum + " docList=" + docList);
    }

    /**
     * 分组查询
     */
    private void queryGroup() {
        Query query = new SimpleQuery("*:*");
        // 请注意，分组查询，offset/limit都不指定时, 会抛异常，Pageable must not be null!
        // 当只指定offset时，limit默认为1； 只指定limit时，offset默认为0
        GroupOptions groupOptions = new GroupOptions().addGroupByField("type").setOffset(0).setLimit(10);
        query.setGroupOptions(groupOptions);

        GroupPage<DocDO> ans = solrTemplate.queryForGroupPage("yhh", query, DocDO.class);
        GroupResult<DocDO> groupResult = ans.getGroupResult("type");

        Page<GroupEntry<DocDO>> entries = groupResult.getGroupEntries();
        System.out.println("============ query for group ============ ");
        for (GroupEntry<DocDO> sub : entries) {
            String groupValue = sub.getGroupValue();
            Page<DocDO> contentList = sub.getResult();
            System.out.println("queryGroup v=" + groupValue + " content=" + contentList.getContent());
        }
        System.out.println("============ query for group ============ ");
    }
}
