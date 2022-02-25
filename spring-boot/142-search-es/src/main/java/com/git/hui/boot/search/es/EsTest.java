package com.git.hui.boot.search.es;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Map;


/**
 * @author yihui
 * @date 2022/1/7
 */
@Service
public class EsTest {
    @Autowired
    private RestHighLevelClient client;

    private static String auth = "Basic ZWxhc3RpYzp0ZXN0MTIz";

    public EsTest(ElasticsearchConfiguration elasticsearchConfiguration) {
        auth = Base64Utils.encodeToString((elasticsearchConfiguration.getUser() + ":" + elasticsearchConfiguration.getPwd()).getBytes());
        auth = "Basic " + auth;
    }

    public void testGet() throws Exception {
        GetRequest getRequest = new GetRequest("first-index", "_doc", "gvarh3gBF9fSFsHNuO49");
        RequestOptions.Builder requestOptions = RequestOptions.DEFAULT.toBuilder();
        requestOptions.addHeader("Authorization", auth);
        GetResponse getResponse = client.get(getRequest, requestOptions.build());
        if (getResponse.isExists()) {
            String sourceAsString = getResponse.getSourceAsString();
            System.out.println(sourceAsString);
        } else {
            System.out.println("no string!");
        }
    }

    public Map<String, Object> searchDataById(String index, String type, String id, String fields) {
        try {
            GetRequest getRequest = new GetRequest(index, type, id);
            RequestOptions.Builder requestOptions = RequestOptions.DEFAULT.toBuilder();
            requestOptions.addHeader("Authorization", auth);
            GetResponse getResponse = client.get(getRequest, requestOptions.build());
            return getResponse.getSource();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
