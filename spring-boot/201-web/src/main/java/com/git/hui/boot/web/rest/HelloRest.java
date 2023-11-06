package com.git.hui.boot.web.rest;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.web.demo.PrintServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by @author yihui in 22:40 19/3/17.
 */
@RestController
public class HelloRest {
    @Autowired
    private PrintServer printServer;

    @GetMapping(path = "hello")
    public String sayHello(HttpServletRequest request) {
        printServer.print();
        return "hello, " + request.getParameter("name");
    }


    @GetMapping({"/", ""})
    public String index() {
        return UUID.randomUUID().toString();
    }


    /**
     * 用于测试返回的结果进行zip压缩的场景
     *
     * @return
     */
    @GetMapping("bigReq")
    public String bigReqList() {
        List<String> result = new ArrayList<>(2048);
        for (int i = 0; i < 2048; i++) {
            result.add(UUID.randomUUID() + ".压缩" + i);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 分块传输的场景
     *
     * @return
     */
    @GetMapping("chuncked")
    public ResponseEntity<StreamingResponseBody> chuncked() {
        StreamingResponseBody responseBody = outputStream -> {
            // 模拟真实场景下连续发送数据
            for (int i = 0; i < 10; i++) {
                // 延迟等待：模拟真实场景
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
                // 数据块编码
                String data = "{\"name\": \"用户名-" + i + "\"}";
                // 写入数据
                outputStream.write(data.getBytes());
                // 发送数据
                outputStream.flush();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }


    /**
     * 模拟本地请求
     *
     * @param args
     */
    public static void main(String[] args) {
//        对于压缩导致的乱码问题，可以考虑使用HttpComponentsClientHttpRequestFactory代替默认的
//        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().forEach(s -> {
            if (s instanceof StringHttpMessageConverter) {
                // 解决中文乱码
                ((StringHttpMessageConverter) s).setDefaultCharset(StandardCharsets.UTF_8);
            }
        });
        HttpHeaders DEFAULT_HEAD = new HttpHeaders();
        // 支持gzip压缩传输
       DEFAULT_HEAD.set("Accept", "*/*");
       DEFAULT_HEAD.set("Accept-Encoding", "gzip,deflate,br");
       DEFAULT_HEAD.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
       DEFAULT_HEAD.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //封装请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(DEFAULT_HEAD);

        String url = "http://localhost:8082/bigReq";
        HttpEntity<String> ans = restTemplate.exchange(url, HttpMethod.GET, formEntity, String.class);
        System.out.println(ans);

        String url2 = "http://localhost:8082/chuncked";
        ans = restTemplate.exchange(url2, HttpMethod.GET, formEntity, String.class);
        System.out.println(ans);
    }
}
