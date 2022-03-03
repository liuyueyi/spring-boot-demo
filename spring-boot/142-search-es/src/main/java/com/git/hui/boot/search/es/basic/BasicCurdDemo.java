package com.git.hui.boot.search.es.basic;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.search.es.ElasticsearchConfiguration;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档最基本操作：增删改查
 *
 * @author yihui
 * @date 2022/3/3
 */
@Component
public class BasicCurdDemo {
    @Autowired
    private RestHighLevelClient client;
    private RequestOptions requestOptions;

    private String TEST_ID = "11123-33345-66543-55231";

    public BasicCurdDemo(ElasticsearchConfiguration elasticsearchConfiguration) {
        String auth = "Basic " + Base64Utils.encodeToString((elasticsearchConfiguration.getUser() + ":" + elasticsearchConfiguration.getPwd()).getBytes());
        RequestOptions.Builder build = RequestOptions.DEFAULT.toBuilder();
        build.addHeader("Authorization", auth);
        requestOptions = build.build();
    }

    public void testOperate() throws IOException {
        String index = "basic_demo";
        Map<String, Object> doc = newMap("name", "一灰灰", "age", 10, "skills", Arrays.asList("java", "python"));
        // 新增
        addDoc(index, doc, TEST_ID);
        // 查询
        get(index, TEST_ID);
        // 更新
        doc.clear();
        doc.put("name", "一灰灰blog");
        doc.put("addr", "hubei");
        updateDoc(index, TEST_ID, doc);
        get(index, TEST_ID);

        updateByCondition(index, newMap("addr", "hubei"), newMap("name", "yihuihui", "site", "https://hhui.top"));
        get(index, TEST_ID);

        // 删除文档
        delete(index, TEST_ID);
    }

    public <K, V> Map<K, V> newMap(K k, V v, Object... kv) {
        Map<K, V> map = new HashMap<>();
        map.put(k, v);
        for (int i = 0; i < kv.length; i += 2) {
            map.put((K) kv[i], (V) kv[i + 1]);
        }
        return map;
    }

    /**
     * 新增数据
     */
    public void addDoc(String indexName, Object obj, String id) throws IOException {
        // 指定索引
        IndexRequest request = new IndexRequest(indexName);
        request.type("_doc");
        // 文档内容，source传参，第一种时按照 fieldName, fieldValue 成对的方式传入；下面是采用json串 + 指定ContentType的方式传入
        request.source(JSON.toJSONString(obj), XContentType.JSON);
        // 指定特殊的id，不指定时自动生成id
        request.id(id);
        IndexResponse response = client.index(request, requestOptions);
        System.out.println("添加数据返回结果: " + response.toString());
    }

    /**
     * 查询结果
     *
     * @param indexName
     * @param id
     * @throws Exception
     */
    public void get(String indexName, String id) throws IOException {
        GetRequest getRequest = new GetRequest(indexName, "_doc", id);
        GetResponse response = client.get(getRequest, requestOptions);
        System.out.println("查询结果:" + response.toString());
    }


    /**
     * 更新文档，根据id进行更新，增量更新，存在的字段，覆盖；新增的字段，插入；旧字段，保留
     *
     * @param indexName
     * @param docId
     * @param obj
     * @throws IOException
     */
    public void updateDoc(String indexName, String docId, Object obj) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.type("_doc");
        updateRequest.id(docId);
        // 设置数据
        updateRequest.doc(JSON.toJSONString(obj), XContentType.JSON);

        UpdateResponse response = client.update(updateRequest, requestOptions);
        System.out.println("更新数据返回：" + response.toString());
    }

    /**
     * 条件更新
     *
     * @param indexName
     * @param query
     * @param data
     * @throws IOException
     */
    public void updateByCondition(String indexName, Map<String, String> query, Map<String, Object> data) throws IOException {
        UpdateByQueryRequest updateRequest = new UpdateByQueryRequest(indexName);
        for (Map.Entry<String, String> entry : query.entrySet()) {
            QueryBuilder queryBuilder = new TermQueryBuilder(entry.getKey(), entry.getValue());
            updateRequest.setQuery(queryBuilder);
        }

        // 更新值脚本，精确的更新方式
        // ctx._source['xx'].add('新增字段')
        // 条件判定 if(ctx._source.addr == 'hubei') { ctx._source.addr = 'wuhan';}
        String source = "ctx._source.name='1hui';";
        Script script = new Script(source);
        updateRequest.setScript(script);

        BulkByScrollResponse response = client.updateByQuery(updateRequest, requestOptions);
        System.out.println("条件更新返回: " + response.toString());
        get(indexName, TEST_ID);
        System.out.println("0---------------------0");

        // 采用全量覆盖式更新，直接使用data中的数据，覆盖之前的文档内容
        source = "ctx._source=params";
        script = new Script(ScriptType.INLINE, "painless", source, data);
        updateRequest.setScript(script);
        response = client.updateByQuery(updateRequest, requestOptions);
        System.out.println("条件更新返回: " + response.toString());
        get(indexName, TEST_ID);
    }


    public void delete(String indexName, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(indexName);
        deleteRequest.type("_doc");
        deleteRequest.id(id);

        DeleteResponse response = client.delete(deleteRequest, requestOptions);
        System.out.println("删除后返回" + response.toString());
    }
}
