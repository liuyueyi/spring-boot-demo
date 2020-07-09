package com.git.hui.boo.webclient.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 09:59 20/7/8.
 */
@Component
public class WebClientTutorial {

    public void test() {
        //        create();
        //        get();
        post();
    }

    public void create() {
        WebClient webClient = WebClient.create();
        Mono<String> ans =
                webClient.get().uri("http://127.0.0.1:8080/get?name=一灰灰&age=18").retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("create return: " + s));


        webClient = WebClient.builder().defaultHeader("User-Agent", "WebClient Agent").build();
        ans = webClient.get().uri("http://127.0.0.1:8080/header").retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("builderCreate with header return: " + s));

        // 请注意WebClient创建完毕之后，不可修改，如果需要设置默认值，可以借助 mutate 继承当前webclient的属性，再进行扩展
        webClient = webClient.mutate().defaultCookie("ck", "--web--client--ck--").build();
        ans = webClient.get().uri("http://127.0.0.1:8080/header").retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("webClient#mutate with cookie return: " + s));
    }

    public void get() {
        // 创建webclient，并指定baseUrl，这里指定的是域名相关，因此后面就只需要带上路径即可
        // 其次请注意，webclient一旦创建，就不可修改了
        WebClient webClient = WebClient.create("http://127.0.0.1:8080");

        Mono<String> ans = webClient.get().uri("/get?name={1}", "一灰灰").retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("basic get with one argument res: " + s));

        // p1对应后面第一个参数 "一灰灰"  p2 对应后面第二个参数 18
        ans = webClient.get().uri("/get?name={p1}&age={p2}", "一灰灰", 18).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("basic get with two arguments res: " + s));


        System.out.println("---------------------  分割线 ----------------------");

        // 使用map的方式，来映射参数
        Map<String, Object> uriVariables = new HashMap<>(4);
        uriVariables.put("p1", "一灰灰");
        uriVariables.put("p2", 19);

        Flux<String> fAns =
                webClient.get().uri("/mget?name={p1}&age={p2}", uriVariables).retrieve().bodyToFlux(String.class);
        fAns.subscribe(s -> System.out.println("basic mget return: " + s));


        // 获取请求头等相关信息

        Mono<ResponseEntity<String>> response = webClient.get().uri("/get?name={p1}&age={p2}", "一灰灰", 18).exchange()
                .flatMap(r -> r.toEntity(String.class));
        response.subscribe(
                entity -> System.out.println("res headers: " + entity.getHeaders() + " body: " + entity.getBody()));
    }


    public void post() {
        WebClient webClient = WebClient.create("http://127.0.0.1:8080");

        // 通过 MultiValueMap 方式投递form表单
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(4);
        formData.add("name", "一灰灰Blog");
        formData.add("age", "18");

        // 请注意，官方文档上提示，默认的ContentType就是"application/x-www-form-urlencoded"，所以下面这个contentType是可以不显示设置的
        Mono<String> ans = webClient.post().uri("/post")
                // .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("post formData ans: " + s));


        // 官方文档上说这种方式可以，然而实际使用时，却是无法解析到参数
        ans = webClient.post().uri("/post").body(BodyInserters.fromFormData(formData)).retrieve()
                .bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("post2 formData ans: " + s));


        // post body

        Body body = new Body();
        body.setName("一灰灰");
        body.setAge(18);
        ans = webClient.post().uri("/body").contentType(MediaType.APPLICATION_JSON).bodyValue(body).retrieve()
                .bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("post body res: " + s));
    }
}
