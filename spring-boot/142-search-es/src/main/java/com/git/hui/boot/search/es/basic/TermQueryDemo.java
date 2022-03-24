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
 * es操作博文
 *
 * <a href='https://blog.hhui.top/hexblog/2021/03/31/210331-ElastchSearch-%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/#2-1-%E6%9F%A5%E8%AF%A2%E6%89%80%E6%9C%89'/>
 * <a href="https://blog.csdn.net/gz1993/article/details/110794944"/>
 *
 * @author yihui
 * @date 2022/3/3
 */
@Component
public class TermQueryDemo {
    private BasicCurdDemo basicCurdDemo;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private RequestOptions requestOptions;

    private String TEST_ID = "11123-33345-66543-55231";
    private String TEST_ID_2 = "11123-33345-66543-55232";

    private String index = "term-demo";

    public TermQueryDemo(BasicCurdDemo basicCurdDemo) throws IOException {
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

    public void testQuery() throws IOException {
        queryAll();
        term();
        term2();
        multTerm();
        range();
        exists();
        match();
        multiMatch();
        wild();
        regexp();
        prefix();
    }

    /**
     * 全量查询
     *
     * @throws IOException
     */
    private void queryAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        int page = 1;
        //每页记录数
        int size = 2;
        //计算出记录起始下标
        int from = (page - 1) * size;
        //起始记录下标，从0开始
        searchSourceBuilder.from(from);
        //每页显示的记录数
        searchSourceBuilder.size(size);
        // 根据age字段进行倒排
        searchSourceBuilder.sort(new FieldSortBuilder("age").order(SortOrder.DESC));
        // 查询所有的文档
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, requestOptions);
        System.out.println("mathAll: " + searchResponse.toString());
    }


    /**
     * term精确查询
     *
     * @throws IOException
     */
    private void term() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        // termQuery: 精确查询
        // SpanTermQuery: 词距查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("site", "blog.hhui.top"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("term: " + response.toString());
    }

    private void term2() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 对于中文查询，需要注意分词的场景, 如果直接使用 "name : 一灰灰" 的方式进行查询，则啥也不会发挥
        // elasticsearch 里默认的IK分词器是会将每一个中文都进行了分词的切割，所以你直接想查一整个词，或者一整句话是无返回结果的。
        // 在此种情况下，我们可以通过制定 keyword 的方式来处理, 设置关键词搜索（不进行分词）
        searchSourceBuilder.query(QueryBuilders.termQuery("name.keyword", "一灰灰"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("term2: " + response.toString());
    }

    /**
     * 相当于in查询
     * {"terms": { "name": ["一灰灰", "二灰灰] }}
     *
     * @throws IOException
     */
    private void multTerm() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termsQuery("name.keyword", "一灰灰", "二灰灰"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("term: " + response.toString());
    }

    /**
     * 范围查询
     * { "range": { "age": { "gt":8, "lt": 12 } }}
     *
     * @throws IOException
     */
    private void range() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("age").gt(8).lt(12));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("range: " + response.toString());
    }

    /**
     * 根据字段是否存在查询
     *
     * @throws IOException
     */
    private void exists() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.existsQuery("site"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("exists: " + response.toString());
    }

    /**
     * 根据字段匹配查询
     *
     * @throws IOException
     */
    private void match() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "灰"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("matchQuery: " + response.toString());
    }

    /**
     * 多字段中查询
     *
     * @throws IOException
     */
    private void multiMatch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("灰", "name", "site"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("multiMatchQuery: " + response.toString());
    }

    /**
     * 模糊查询 ? 单字符  * 0..n字符
     *
     * @throws IOException
     */
    private void wild() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.wildcardQuery("site", "*top"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("wildcard: " + response.toString());
    }

    /**
     * 模糊查询 ? 单字符  * 0..n字符
     *
     * @throws IOException
     */
    private void regexp() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.regexpQuery("site", ".*hhui.*"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("regexpQuery: " + response.toString());
    }

    private void prefix() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.prefixQuery("site", "blog"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, requestOptions);
        System.out.println("prefixQuery: " + response.toString());
    }
}
