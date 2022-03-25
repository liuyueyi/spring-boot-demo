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
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.min.ParsedMin;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
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
    private String TEST_ID_3 = "11123-33345-66543-55233";

    private String index = "aggregate-demo";

    public AggregateQueryDemo(BasicCurdDemo basicCurdDemo) throws IOException {
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
        basicCurdDemo.delete(index, TEST_ID);
    }

    public void test() throws IOException {
        mustCondition();
        shouldCondition();
        querySpecialFields();
        groupBy();
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

    /**
     * 分组,计算
     *
     * @throws IOException
     */
    private void groupBy() throws IOException {
        // 分组 + 计算
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("user_group").field("user.keyword")
                .subAggregation(AggregationBuilders.avg("avg_age").field("age"))
                .subAggregation(AggregationBuilders.min("min_age").field("age"))
                .subAggregation(AggregationBuilders.max("max_age").field("age"))
                .subAggregation(AggregationBuilders.sum("sum_age").field("age"));
        searchSourceBuilder.aggregation(aggregationBuilder);
        System.out.println("groupBy dsl:" + searchSourceBuilder.toString());

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);

        Aggregations aggregations = response.getAggregations();
        ParsedStringTerms parsedStringTerms = aggregations.get("user_group");
        for (Terms.Bucket bucket : parsedStringTerms.getBuckets()) {
            String key = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            Aggregations data = bucket.getAggregations();
            ParsedAvg avg = data.get("avg_age");
            ParsedMin min = data.get("min_age");
            ParsedMax max = data.get("max_age");
            ParsedSum sum = data.get("sum_age");
            System.out.println("group by: " + key + "#" + docCount + ": avg=" + avg.getValueAsString()
                    + " min=" + min.getValueAsString() + " max=" + max.getValueAsString() + " sum=" + sum.getValueAsString());
        }
        System.out.println("groupBy:" + response.toString());
    }
}
