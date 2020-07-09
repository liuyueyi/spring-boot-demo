package com.git.hui.boo.webclient.rest;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    @PostMapping(path = "post2")
    public Mono<String> post2(String name, Integer age) {
        return Mono.just("post2 req name " + name + " age " + age);
    }

    /**
     * 接收form表单的第二种姿势
     *
     * @param request
     * @return
     */
    @PostMapping(path = "post3", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
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
    @PostMapping(path = "post4", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> post4(@RequestParam Map<String, String> params) {
        return Mono.just("post4 req: " + JSON.toJSONString(params));
    }


    @PostMapping(path = "body")
    public Mono<String> postBody(@RequestBody Body body) {
        return Mono.just("body req: " + body);
    }


    @Autowired
    private WebClientTutorial webClientTuration;

    @GetMapping(path = "test")
    public String test() {
        webClientTuration.test();
        return "over";
    }
}
