package com.git.hui.boot.search.es.basic;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static com.git.hui.boot.search.es.MapUtil.newMap;

/**
 * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-search.html
 * <p>
 * 聚合查询实例case
 * - 分组
 * - 计数
 * - 指定字段查询
 * - 排序
 * - 多条件查询
 *
 * @author wuzebang
 * @date 2022/3/22
 */
@Component
public class AggregateQueryDemo {
    private BasicCurdDemo basicCurdDemo;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private RequestOptions requestOptions;

    private String TEST_ID = "11123-33345-66543-55231";
    private String TEST_ID_2 = "11123-33345-66543-55232";

    private String index = "aggregate-demo";

    public AggregateQueryDemo(BasicCurdDemo basicCurdDemo) throws IOException {
        this.basicCurdDemo = basicCurdDemo;
        Map<String, Object> doc = newMap("name", "一灰灰", "age", 10, "skills", Arrays.asList("java", "python"), "site", "blog.hhui.top");
        basicCurdDemo.addDoc(index, doc, TEST_ID);
        doc = newMap("name", "二灰灰", "age", 16, "skills", Arrays.asList("js", "html"));
        basicCurdDemo.addDoc(index, doc, TEST_ID_2);
    }

    @PreDestroy
    public void remove() throws IOException {
        basicCurdDemo.delete(index, TEST_ID);
        basicCurdDemo.delete(index, TEST_ID);
    }

    public void test() throws IOException {
        mustCondition();
        shouldCondition();
        querySpecialFields();
        avgQuery();
    }

    /**
     * 多条件查询
     */
    private void mustCondition() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(10000);
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name.keyword", "一灰灰");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").gte(10).lt(20);

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        // 多条件同时满足
        boolBuilder.must(queryBuilder);
        boolBuilder.must(rangeQueryBuilder);
        searchSourceBuilder.query(boolBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, requestOptions);
        System.out.println("mustCondition: " + searchResponse.toString());
    }

    /**
     * or条件查询
     *
     * @throws IOException
     */
    private void shouldCondition() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(10000);
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name.keyword", "一灰灰");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").gte(10).lt(20);

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        // 多条件满足一个即可，相当于or
        boolBuilder.should(queryBuilder);
        boolBuilder.should(rangeQueryBuilder);
        searchSourceBuilder.query(boolBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, requestOptions);
        System.out.println("shouldCondition: " + searchResponse.toString());
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

    private void avgQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("age").field("age")
                .subAggregation(AggregationBuilders.avg("age_avg").field("age"));
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchResponse searchResponse = client.search(searchRequest, requestOptions);
        System.out.println("avgQuery:" + searchResponse.toString());
    }
}
