package com.git.hui.boot.search.es.basic;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static com.git.hui.boot.search.es.MapUtil.newMap;

/**
 * 高级使用姿势
 *
 * @author yihui
 * @date 2022/3/31
 */
@Component
public class AdvancedQueryDemo {

    private BasicCurdDemo basicCurdDemo;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private RequestOptions requestOptions;

    private String TEST_ID = "11123-33345-66543-55231";
    private String TEST_ID_2 = "11123-33345-66543-55232";
    private String TEST_ID_3 = "11123-33345-66543-55233";

    private String index = "advance-demo";

    public AdvancedQueryDemo(BasicCurdDemo basicCurdDemo) throws IOException {
        this.basicCurdDemo = basicCurdDemo;
        Map<String, Object> doc = newMap("user", "yihui", "name", "一灰灰", "age", 10, "skills", Arrays.asList("java", "python"), "site", "blog.hhui.top");
        basicCurdDemo.addDoc(index, doc, TEST_ID);
        doc = newMap("user", "erhui", "name", "二灰灰", "age", 16, "skills", Arrays.asList("js", "html"));
        basicCurdDemo.addDoc(index, doc, TEST_ID_2);
        doc = newMap("user", "yihui", "name", "二灰灰", "age", 18, "skills", Arrays.asList("c#", ".net"));
        basicCurdDemo.addDoc(index, doc, TEST_ID_3);
    }

    @PreDestroy
    public void remove() throws IOException {
        basicCurdDemo.delete(index, TEST_ID);
        basicCurdDemo.delete(index, TEST_ID_2);
        basicCurdDemo.delete(index, TEST_ID_3);
    }

    public void test() throws IOException {
        querySpecialFields();
        sort();
        limit();
    }


    /**
     * 查询指定的字段内容
     *
     * @throws IOException
     */
    private void querySpecialFields() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] includeFields = new String[]{"name", "age", "site"};
        String[] excludeFields = new String[]{"_type"};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, requestOptions);
        System.out.println("querySpecialFields:" + searchResponse.toString());
    }

    /**
     * 排序
     *
     * @throws IOException
     */
    private void sort() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        // 设置为根据age进行正向排序
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(new FieldSortBuilder("age").order(SortOrder.ASC));

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("sort response:" + response.toString());
    }

    /**
     * 条数限制
     *
     * @throws IOException
     */
    private void limit() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        // 设置为根据age进行正向排序
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 每页显示的记录数
        searchSourceBuilder.size(2);

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("limit response:" + response.toString());
    }
}
