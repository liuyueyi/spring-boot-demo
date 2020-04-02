import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by @author yihui in 14:45 20/3/29.
 */
public class HttpClientTest {

    @Test
    public void testGet() throws IOException {
        try {
            HttpGet get =
                    new HttpGet("http://root:123@127.0.0.1:8983/solr/yhh/get?qt=%2Fget&ids=1&wt=javabin&version=2");
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity responseEntity = response.getEntity();

            String ans = " " + IOUtils.toString(responseEntity.getContent(), "UTF-8");
            System.out.println(ans);
        } catch (Exception e) {

        }
    }

    @Test
    public void testPost() throws IOException {
        try {
            HttpPost post = new HttpPost("http://root:123@127.0.0.1:8983/solr/yhh/update?wt=javabin&version=2");
            post.addHeader("Content-Type", "application/json");

            JSONArray jsonArray = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("id", "122");
            jsonArray.add(obj);
            StringEntity s = new StringEntity(jsonArray.toJSONString());
            post.setEntity(s);


            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();

            String ans = " " + IOUtils.toString(responseEntity.getContent(), "UTF-8");
            System.out.println(ans);
        } catch (Exception e) {

        }
    }

}
