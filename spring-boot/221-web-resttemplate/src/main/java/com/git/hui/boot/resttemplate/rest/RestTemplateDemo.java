package com.git.hui.boot.resttemplate.rest;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

/**
 * Created by @author yihui in 19:45 20/6/16.
 */
@Slf4j
@Component
public class RestTemplateDemo {

    public void test() {
        System.out.println("\n=========== get实例 =============\n");
        basicGet();
        System.out.println("\n=========== post实例 =============\n");
        basicPost();
        System.out.println("\n=========== post body实例 =============\n");
        jsonPost();
        System.out.println("\n=========== 中文乱码 =============\n");
        chinese();
        System.out.println("\n=========== 请求头 =============\n");
        header();
    }

    /**
     * 基本的get请求
     */
    public void basicGet() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> res =
                restTemplate.getForEntity("http://127.0.0.1:8080/get?name=一灰灰Blog&age=20", String.class);
        log.info("Simple getForEntity res: {}", res);


        String response = restTemplate.getForObject("http://127.0.0.1:8080/get?name=一灰灰Blog&age=20", String.class);
        log.info("Simple getForObject res: {}", response);


        response = restTemplate.getForObject("http://127.0.0.1:8080/get?name={?}&age={?}", String.class, "一灰灰Blog", 20);
        log.info("Simple getForObject by uri params: {}", response);

        response = restTemplate.getForObject("http://127.0.0.1:8080/get?name={name}&age={age}", String.class,
                new HashMap<String, Object>() {
                    {
                        put("name", "一灰灰Blog");
                        put("age", 20);
                    }
                });
        log.info("Simple getForObject by uri map params: {}", response);
    }

    /**
     * post基本请求
     */
    public void basicPost() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> res = restTemplate
                .postForEntity("http://127.0.0.1:8080/post?name=一灰灰Blog&age=20", new LinkedMultiValueMap<>(),
                        String.class);
        log.info("Simple postForEntity res: {}", res);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", "一灰灰Blog");
        params.add("age", 20);

        String response = restTemplate.postForObject("http://127.0.0.1:8080/post", params, String.class);
        log.info("Simple postForObject res: {}", response);

        response = restTemplate.postForObject("http://127.0.0.1:8080/post?urlName={?}", params, String.class, "url参数");
        log.info("Simple postForObject with urlParams res: {}", response);

        response = restTemplate.postForObject("http://127.0.0.1:8080/post?urlName={urlName}", params, String.class,
                new HashMap<String, Object>() {{
                    put("urlName", "url参数");
                }});
        log.info("Simple postForObject with map urlParams res: {}", response);
    }


    /**
     * json表单
     */
    public void jsonPost() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject params = new JSONObject();
        params.put("name", "一灰灰Blog");
        params.put("age", 20);

        HttpEntity<String> request = new HttpEntity<>(params.toJSONString(), headers);

        String response = restTemplate.postForObject("http://127.0.0.1:8080/body", request, String.class);
        log.info("json post res: {}", response);
    }


    /**
     * 中文乱码问题fix
     */
    public void chinese() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject params = new JSONObject();
        params.put("name", "一灰灰Blog");
        params.put("age", 20);

        HttpEntity<String> request = new HttpEntity<>(params.toJSONString(), headers);

        // 中文乱码，主要是 StringHttpMessageConverter的默认编码为ISO导致的
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter converter : list) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }

        String response = restTemplate.postForObject("http://127.0.0.1:8080/body", request, String.class);
        log.info("json post res: {}", response);
    }

    /**
     * 带请求头的访问姿势
     */
    public void header() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        headers.set("cookie", "my_user_id=haha123; UN=1231923;gr_user_id=welcome_yhh;");

        HttpEntity<String> res = restTemplate
                .exchange("http://127.0.0.1:8080/get?name=一灰灰&age=20", HttpMethod.GET, new HttpEntity<>(null, headers),
                        String.class);
        log.info("get with selfDefine header: {}", res);

        // post 带请求头
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", "一灰灰Blog");
        params.add("age", 20);

        String response = restTemplate
                .postForObject("http://127.0.0.1:8080/post", new HttpEntity<>(params, headers), String.class);
        log.info("post with selfDefine header: {}", response);


        // 借助拦截器的方式来实现塞统一的请求头
        ClientHttpRequestInterceptor interceptor = (httpRequest, bytes, execution) -> {
            httpRequest.getHeaders().set("user-agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
            httpRequest.getHeaders().set("cookie", "my_user_id=haha123; UN=1231923;gr_user_id=interceptor;");
            return execution.execute(httpRequest, bytes);
        };
        restTemplate.getInterceptors().add(interceptor);
        response = restTemplate.getForObject("http://127.0.0.1:8080/get?name=一灰灰&age=20", String.class);
        log.info("get with selfDefine header by Interceptor: {}", response);
    }

    /**
     * 设置超时时间
     */
    public void timeOut() {

    }


    public void factory() {

    }

    /**
     * basic auth 的访问姿势
     */
    public void auth() {

    }

    /**
     * 接口返回非200时，也需要获取返回的结果
     */
    public void exception() {

    }


    public void ssl() {

    }
}
