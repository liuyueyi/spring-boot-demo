import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
        HttpGet get = new HttpGet("http://admin:123@localhost:8080/call/auth");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity responseEntity = response.getEntity();
        System.out.println(responseEntity);
    }

    @Test
    public void testPost() throws IOException {
        HttpPost post = new HttpPost("http://admin:123@localhost:8080/call/auth");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity responseEntity = response.getEntity();
        System.out.println(responseEntity);
    }

}
