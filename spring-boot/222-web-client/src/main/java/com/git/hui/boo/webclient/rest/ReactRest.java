package com.git.hui.boo.webclient.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by @author yihui in 09:15 20/7/8.
 */
@RestController
public class ReactRest {

    @GetMapping(path = "header")
    public Mono<String> header(@RequestHeader(name = "User-Agent") String userAgent,
            @CookieValue(name = "ck", required = false) String cookie) {
        return Mono.just("userAgent is: [" + userAgent + "] cookie: [" + cookie + "]");
    }

    @GetMapping(path = "get")
    public Mono<String> get(String name, Integer age) {
        return Mono.just("req: " + name + " age: " + age);
    }

    @GetMapping(path = "mget")
    public Flux<String> mget(String name, Integer age) {
        return Flux.fromArray(new String[]{"req name: " + name, "req age: " + age});
    }


    /**
     * form表单传参，映射到实体上
     *
     * @param body
     * @return
     */
    @PostMapping(path = "post")
    public Mono<String> post(Body body) {
        return Mono.just("post req: " + body.getName() + " age: " + body.getAge());
    }


    /**
     * 接收form表单参数时，并不能正确获取参数内容
     *
     * @param name
     * @param age
     * @return
     */
    @PostMapping(path = "post2", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> post2(String name, Integer age) {
        return Mono.just("post2 req name " + name + " age " + age);
    }

    /**
     * 接收form表单的第二种姿势
     *
     * @param request
     * @return
     */
    @PostMapping(path = "post3", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> post3(ServerWebExchange request) {
        Mono<MultiValueMap<String, String>> form = request.getFormData();
        return form.flatMap(s -> Mono.just("post3 req: " + JSON.toJSONString(s)));
    }

    /**
     * 请注意，这种写法，也获取不到post表单的参数
     *
     * @param params
     * @return
     */
    @PostMapping(path = "post4", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> post4(@RequestParam Map<String, String> params) {
        return Mono.just("post4 req: " + JSON.toJSONString(params));
    }


    @PostMapping(path = "body")
    public Mono<String> postBody(@RequestBody Body body) {
        return Mono.just("body req: " + body);
    }

    @PostMapping(path = "body2")
    public Mono<Body> postBody2(@RequestBody  Body body) {
        return Mono.just(body);
    }


    /**
     * 文件上传
     *
     * @param filePart
     * @return
     */
    @PostMapping(path = "upload", produces = MediaType.MULTIPART_MIXED_VALUE)
    public Mono<String> upload(@RequestPart(name = "data") FilePart filePart, ServerWebExchange exchange)
            throws IOException {
        // 使用 exchagne.getFormData 获取不到表单参数，因此改成下面的写法
        //        Mono<MultiValueMap<String, String>> form = exchange.getFormData();
        //
        //        StringBuilder builder = new StringBuilder("upload data: ");
        //
        //        filePart.content().subscribe(s -> {
        //            byte[] bytes = new byte[s.readableByteCount()];
        //            s.read(bytes);
        //            builder.append(new String(bytes));
        //        });
        //
        //        form.subscribe(s -> builder.append("|params:").append(JSON.toJSONString(s)));
        //        return Mono.just(builder.toString());
        Mono<MultiValueMap<String, Part>> ans = exchange.getMultipartData();

        StringBuffer result = new StringBuffer("【basic uploads: ");
        ans.subscribe(s -> {
            for (Map.Entry<String, List<Part>> entry : s.entrySet()) {
                for (Part part : entry.getValue()) {
                    result.append(entry.getKey()).append(":");
                    dataBuffer2str(part.content(), result);
                }
            }
        });

        result.append("】");
        return Mono.just(result.toString());
    }

    private void dataBuffer2str(Flux<DataBuffer> data, StringBuffer buffer) {
        data.subscribe(s -> {
            byte[] bytes = new byte[s.readableByteCount()];
            s.read(bytes);
            buffer.append(new String(bytes)).append(";");
        });
    }

    @GetMapping(path = "timeout")
    public Mono<String> timeout(String name, Integer age) throws InterruptedException {
        Thread.sleep(5_000);
        return Mono.just("timeout req: " + name + " age: " + age);
    }


    /**
     * 返回请求头
     *
     * @return
     */
    @GetMapping(path = "withHeader")
    public Mono<String> withHeader(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        MultiValueMap<String, String> params = request.getQueryParams();
        return Mono.just("-->headers: " + JSONObject.toJSONString(headers) + ";\t-->params:" +
                JSONObject.toJSONString(params));
    }

    @GetMapping(path = "403")
    public Mono<String> _403(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return Mono.just("403 response body!");
    }

    @GetMapping(path = "auth")
    public Mono<String> auth(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        List<String> authList = request.getHeaders().get("Authorization");
        if (CollectionUtils.isEmpty(authList)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return Mono.just("no auth info!");
        }

        String auth = authList.get(0);
        String[] userAndPass = new String(new BASE64Decoder().decodeBuffer(auth.split(" ")[1])).split(":");
        if (userAndPass.length < 2) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return Mono.just("illegal auth info!");
        }

        if (!("user".equalsIgnoreCase(userAndPass[0]) && "pwd".equalsIgnoreCase(userAndPass[1]))) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return Mono.just("error auth info!");
        }


        return Mono.just("auth success: " + JSONObject.toJSONString(request.getQueryParams()));
    }

    @Autowired
    private WebClientTutorial webClientTuration;

    @GetMapping(path = "test")
    public String test() {
        webClientTuration.test();
        return "over";
    }
}
