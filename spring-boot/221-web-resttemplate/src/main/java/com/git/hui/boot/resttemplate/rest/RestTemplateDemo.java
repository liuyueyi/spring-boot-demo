package com.git.hui.boot.resttemplate.rest;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by @author yihui in 19:45 20/6/16.
 */
@Slf4j
@Component
public class RestTemplateDemo {

    public void test() {
        //        System.out.println("\n=========== get实例 =============\n");
        //        basicGet();
        //        System.out.println("\n=========== post实例 =============\n");
        //        basicPost();
        System.out.println("\n=========== post body实例 =============\n");
        jsonPost();
        System.out.println("\n=========== 中文乱码 =============\n");
        chinese();
        System.out.println("\n=========== 请求头 =============\n");
        header();
        System.out.println("\n=========== 请求头错误case =============\n");
        errorHeader();
        System.out.println("\n=========== 超时 =============\n");
        timeOut();
        System.out.println("\n=========== 代理 =============\n");
        proxy();
        System.out.println("\n=========== 授权验证 =============\n");
        auth();
        System.out.println("\n=========== 异常 =============\n");
        exception();
        System.out.println("\n=========== ssl =============\n");
        ssl();
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
                break;
            }
        }

        String response = restTemplate.postForObject("http://127.0.0.1:8080/body", request, String.class);
        log.info("json post res: {}", response);

        // 直接传一个POJO

        restTemplate = new RestTemplate();
        InnerParam innerParam = new InnerParam("一灰灰Blog", 20);
        HttpEntity entity = new HttpEntity<>(innerParam, headers);
        response = restTemplate.postForObject("http://127.0.0.1:8080/body", entity, String.class);
        log.info("json post DO res: {}", response);


        Map<String, String> map = new HashMap<>(4);
        map.put("name", "一灰灰Blog");
        map.put("age", "20");
        entity = new HttpEntity<>(map, headers);
        response = restTemplate.postForObject("http://127.0.0.1:8080/body", entity, String.class);
        log.info("json post Map res: {}", response);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerParam implements Serializable {
        private static final long serialVersionUID = -3693060057697231136L;
        private String name;
        private Integer age;
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
     * 错误的请求头使用姿势
     */
    public void errorHeader() {
        RestTemplate restTemplate = new RestTemplate();

        int i = 0;
        // 为了复用headers，避免每次都创建这个对象，但是在循环中又是通过 add 方式添加请求头，那么请求头会越来越膨胀，最终导致请求超限
        // 这种case，要么将add改为set；要么不要在循环中这么干
        HttpHeaders headers = new HttpHeaders();
        while (++i < 5) {
            headers.add("user-agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
            headers.add("cookie", "my_user_id=haha123; UN=1231923;gr_user_id=welcome_yhh;");

            HttpEntity<String> res = restTemplate.exchange("http://127.0.0.1:8080/get?name=一灰灰&age=20", HttpMethod.GET,
                    new HttpEntity<>(null, headers), String.class);
            log.info("get with selfDefine header: {}", res);
        }
    }

    /**
     * 设置超时时间
     */
    public void timeOut() {
        RestTemplate restTemplate = new RestTemplate();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(1000);
        restTemplate.setRequestFactory(requestFactory);
        long start = System.currentTimeMillis();
        try {
            log.info("timeOut start: {}", start);
            HttpEntity<String> response =
                    restTemplate.getForEntity("http://127.0.0.1:8080/timeout?name=一灰灰&age=20", String.class);
            log.info("timeOut cost:{} response: {}", System.currentTimeMillis() - start, response);
        } catch (Exception e) {
            log.info("timeOut cost:{} exception: {}", System.currentTimeMillis() - start, e.getMessage());
        }
    }

    /**
     * 代理访问
     */
    public void proxy() {
        RestTemplate restTemplate = new RestTemplate();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 请注意，我这里是在241机器上，借助tinyproxy搭建了一个http的代理，并设置端口为18888，所以可以正常演示代理访问
        // 拉源码运行的小伙，需要注意使用自己的代理来替换
        requestFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.241", 18888)));

        restTemplate.setRequestFactory(requestFactory);

        // 因为使用代理访问，所以这个ip就不能是127.0.0.1，不然访问的就是代理服务器上了
        HttpEntity<String> ans =
                restTemplate.getForEntity("http://192.168.0.174:8080/proxy?name=一灰灰&age=20", String.class);
        log.info("proxy request ans: {}", ans.getBody());
    }

    /**
     * basic auth 的访问姿势
     */
    public void auth() {
        RestTemplate restTemplate = new RestTemplate();

        // 1. 最原始的办法，直接在请求头中处理
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64Utils.encodeToString("user:pwd".getBytes()));

        HttpEntity<String> ans = restTemplate
                .exchange("http://127.0.0.1:8080/auth?name=一灰灰&age=20", HttpMethod.GET, new HttpEntity<>(null, headers),
                        String.class);
        log.info("auth by direct headers: {}", ans);


        // 2. 借助拦截器的方式来实现塞统一的请求头
        ClientHttpRequestInterceptor interceptor = (httpRequest, bytes, execution) -> {
            httpRequest.getHeaders().set("Authorization", "Basic " + Base64Utils.encodeToString("user:pwd".getBytes()));
            return execution.execute(httpRequest, bytes);
        };
        restTemplate.getInterceptors().add(interceptor);
        ans = restTemplate.getForEntity("http://127.0.0.1:8080/auth?name=一灰灰&age=20", String.class);
        log.info("auth by interceptor: {}", ans);


        // 3. 实际上RestTemplate提供了标准的验证拦截器
        restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("user", "pwd"));
        ans = restTemplate.getForEntity("http://127.0.0.1:8080/auth?name=一灰灰&age=20", String.class);
        log.info("auth by interceptor: {}", ans);


        // 4. 创建 RestTemplate时指定
        restTemplate = new RestTemplateBuilder().basicAuthentication("user", "pwd").build();
        ans = restTemplate.getForEntity("http://127.0.0.1:8080/auth?name=一灰灰&age=20", String.class);
        log.info("auth by RestTemplateBuilder: {}", ans);


        try {
            // 直接在url中，添加用户名+密码，但是没有额外处理时，并不会生效
            restTemplate = new RestTemplate();
            ans = restTemplate.getForEntity("http://user:pwd@127.0.0.1:8080/auth?name=一灰灰&age=20", String.class);
            log.info("auth by url mode: {}", ans);
        } catch (Exception e) {
            log.info("auth exception: {}", e.getMessage());
        }
    }

    /**
     * 接口返回非200时，也需要获取返回的结果
     */
    public void exception() {
        try {
            // 如果返回状态码不是200，则直接抛异常，无法拿到responseBody
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> ans =
                    restTemplate.getForEntity("http://127.0.0.1:8080/auth?name=一灰灰&age=20", String.class);
            log.info("exception with no auth res: {}", ans);
        } catch (Exception e) {
            log.info("exception with no auth error: {}", e);
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                log.info("some error!");
            }
        });
        HttpEntity<String> ans = restTemplate.getForEntity("http://127.0.0.1:8080/auth?name=一灰灰&age=20", String.class);
        log.info("exception with no auth after errorHandler res: {}", ans);
    }


    public void ssl() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("https://story.hhui.top/", String.class);
        log.info("ssl response: {}", response);
    }
}
