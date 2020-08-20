package com.git.hui.boo.webclient.rest;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by @author yihui in 09:59 20/7/8.
 */
@Component
public class WebClientTutorial {
    private void hook(long time, String tag) {
        // 避免线程直接退出，导致没有输出
        try {
            Thread.sleep(time);
            System.out.println("\n=============" + tag + "==============\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        //        create();
        //        hook(3000, "get");
        //        get();
        //        hook(3000, "post");
        //        post();
        //        hook(3000, "upload");
        //        postFile();
        //        hook(3000, "headers");
        //        headers();
        //        hook(3000, "auth");
        //        auth();
        hook(3000, "strategy");
        strategy();
        //        hook(3000, "response detail");
        //        responseDetail();
        //        hook(3000, "error");
        //        errorCase();
        //        hook(3000, "timeout");
        //        timeout();
        //        hook(10000, "block");
        //        sync();
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


        // 官方文档上说这种方式可以，请注意实际使用时，这里是body而不是bodyvalue
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


    /**
     * 文件上传的方式
     */
    public void postFile() {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("data",
                new FileSystemResource(this.getClass().getClassLoader().getResource("test.txt").getFile()));

        // 表单参数
        builder.part("name", "一灰灰");

        MultiValueMap<String, HttpEntity<?>> parts = builder.build();

        WebClient webClient = WebClient.create("http://127.0.0.1:8080");
        Mono<String> ans = webClient.post().uri("/upload").bodyValue(parts).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("upload file return : " + s));


        // 以流的方式上传资源
        builder = new MultipartBodyBuilder();
        final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("test.txt");
        builder.part("data", new InputStreamResource(stream) {
            @Override
            public long contentLength() throws IOException {
                // 这个方法需要重写，否则无法正确上传文件；原因在于父类是通过读取流数据来计算大小
                return stream.available();
            }

            @Override
            public String getFilename() {
                return "test.txt";
            }
        });
        parts = builder.build();
        ans = webClient.post().uri("/upload").bodyValue(parts).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("upload stream return: " + s));


        // 以字节数组的方式上传资源
        builder = new MultipartBodyBuilder();
        builder.part("data", new ByteArrayResource("hello 一灰灰😝!!!".getBytes()) {
            @Override
            public String getFilename() {
                return "test.txt";
            }
        });
        parts = builder.build();
        ans = webClient.post().uri("/upload").bodyValue(parts).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("upload bytes return: " + s));


        // 多文件上传
        builder.part("data", new ByteArrayResource("welcome 二灰灰😭!!!".getBytes()) {
            @Override
            public String getFilename() {
                return "test2.txt";
            }
        });
        parts = builder.build();
        ans = webClient.post().uri("/upload").bodyValue(parts).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("batch upload bytes return: " + s));


        // 第二种上传姿势
        ans = webClient.post().uri("/upload").body(BodyInserters.fromMultipartData("data",
                new FileSystemResource(this.getClass().getClassLoader().getResource("test.txt").getFile()))
                .with("name", "form参数")).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("upload file build by BodyInserters return: " + s));
    }


    /**
     * 携带请求头
     */
    public void headers() {
        // 1. 在创建时，指定默认的请求头
        WebClient webClient = WebClient.builder().defaultHeader("User-Agent", "SelfDefine Header")
                .defaultHeader("referer", "localhost").baseUrl("http://127.0.0.1:8080").build();

        Mono<String> ans =
                webClient.get().uri("/withHeader?name={1}&age={2}", "一灰灰", 19).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("basic get with default header return: " + s));


        // 2. 使用filter
        webClient = WebClient.builder().filter((request, next) -> {
            ClientRequest filtered = ClientRequest.from(request).header("filter-header", "self defined header").build();
            return next.exchange(filtered);
        }).baseUrl("http://127.0.0.1:8080").build();
        ans = webClient.get().uri("/withHeader?name={1}&age={2}", "一灰灰", 19).retrieve().bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("basic get with filter header return: " + s));
    }


    /**
     * basic auth
     */
    public void auth() {
        // 最原始的请求头设置方式
        WebClient webClient = WebClient.builder()
                .defaultHeader("Authorization", "Basic " + Base64Utils.encodeToString("user:pwd".getBytes()))
                .baseUrl("http://127.0.0.1:8080").build();
        Mono<ResponseEntity<String>> response =
                webClient.get().uri("/auth?name=一灰灰&age=18").exchange().flatMap(s -> s.toEntity(String.class));
        response.subscribe(s -> System.out.println("header auth return: " + s));


        // filter方式
        webClient = WebClient.builder().filter(ExchangeFilterFunctions.basicAuthentication("user", "pwd"))
                .baseUrl("http://127.0.0.1:8080").build();
        response = webClient.get().uri("/auth?name=一灰灰&age=18").exchange().flatMap(s -> s.toEntity(String.class));

        response.subscribe(s -> System.out.println("filter auth return: " + s));
    }


    public void strategy() {
        WebClient webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder().codecs(codec -> {
            codec.customCodecs().decoder(new Jackson2JsonDecoder());
            codec.customCodecs().encoder(new Jackson2JsonEncoder());
        }).build()).baseUrl("http://127.0.0.1:8080").build();
        Body body = new Body("一灰灰😝", 18);
        Mono<Body> ans =
                webClient.post().uri("/body2").contentType(MediaType.APPLICATION_JSON).bodyValue(body).retrieve()
                        .bodyToMono(Body.class);
        ans.subscribe(s -> System.out.println("retreive res: " + s));

        hook(3000, "-----");

        // 默认允许的内存空间大小为256KB，可以通过下面的方式进行修改
        webClient = WebClient.builder().exchangeStrategies(
                ExchangeStrategies.builder().codecs(codec -> codec.defaultCodecs().maxInMemorySize(10)).build())
                .baseUrl("http://127.0.0.1:8080").build();

        String argument = "这也是一个很长很长的文本，用于测试超出上限!";
        Mono<String> ans2 = webClient.get().uri("/get?name={1}", argument).retrieve().bodyToMono(String.class)
                .doOnError(WebClientResponseException.class, err -> {
                    System.out.println(err.getRawStatusCode() + "," + err.getResponseBodyAsString());
                    throw new RuntimeException(err.getMessage());
                }).onErrorReturn("fallback");
        ans.subscribe(s -> System.out.println("exchange strategy: " + ans2));
    }


    public void responseDetail() {
        // 利用 exchange 获取更详细的返回信息

        WebClient webClient = WebClient.create("http://127.0.0.1:8080");

        // 返回结果
        Mono<ClientResponse> res = webClient.get().uri("/get?name={1}&age={2}", "一灰灰", 18).exchange();
        res.subscribe(s -> {
            HttpStatus statusCode = s.statusCode();
            ClientResponse.Headers headers = s.headers();
            MultiValueMap<String, ResponseCookie> ans = s.cookies();
            s.bodyToMono(String.class).subscribe(body -> {
                System.out.println("response detail: \nheader: " + headers.asHttpHeaders() + "\ncode: " + statusCode +
                        "\ncookies: " + ans + "\nbody:" + body);
            });
        });


        Mono<ResponseEntity<String>> ans = webClient.get().uri("/get?name={1}&age={2}", "一灰灰", 18).exchange()
                .flatMap(s -> s.toEntity(String.class));

        ans.subscribe(s -> {
            HttpStatus statusCode = s.getStatusCode();
            HttpHeaders headers = s.getHeaders();
            String body = s.getBody();
            System.out.println("response detail2: \ncode: " + statusCode + "\nheaders: " + headers + "\nbody: " + body);
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void errorCase() {
        // 返回非200状态码的处理方式
        // 区分 exchange 与 retrieve 两种使用姿势

        WebClient webClient = WebClient.create("http://127.0.0.1:8080");

        try {
            Mono<String> ans = webClient.get().uri("403").retrieve().bodyToMono(String.class);
            ans.subscribe(s -> System.out.println("403 ans: " + s));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        hook(3000, "---");

        Mono<String> ans = webClient.get().uri("403").retrieve().onStatus(HttpStatus::is4xxClientError, response -> {
            System.out.println(
                    "inner retrieve 403 res: " + response.headers().asHttpHeaders() + "|" + response.statusCode());
            response.bodyToMono(String.class).subscribe(s -> System.out.println("inner res body: " + s));
            return Mono.just(new RuntimeException("403 not found!"));
        }).bodyToMono(String.class);
        ans.subscribe(s -> System.out.println("retrieve 403 ans: " + s));

        hook(3000, "------");

        webClient.get().uri("403").exchange().subscribe(s -> {
            HttpStatus statusCode = s.statusCode();
            ClientResponse.Headers headers = s.headers();
            MultiValueMap<String, ResponseCookie> cookies = s.cookies();
            s.bodyToMono(String.class).subscribe(body -> {
                System.out.println("exchange error response detail: \nheader: " + headers.asHttpHeaders() + "\ncode: " +
                        statusCode + "\ncookies: " + cookies + "\nbody:" + body);
            });
        });
    }

    public void timeout() {
        // 超时相关设置

        // 设置读写超时时间，设置连接超时时间
        HttpClient httpClient = HttpClient.create().tcpConfiguration(
                client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3_000).doOnConnected(
                        conn -> conn.addHandlerLast(new ReadTimeoutHandler(3))
                                .addHandlerLast(new WriteTimeoutHandler(3))));
        // 设置httpclient
        WebClient webClient = WebClient.builder().baseUrl("http://127.0.0.1:8080")
                .clientConnector(new ReactorClientHttpConnector(httpClient)).build();

        Mono<ResponseEntity<String>> ans =
                webClient.get().uri("/timeout").exchange().flatMap(s -> s.toEntity(String.class));
        ans.subscribe(s -> System.out.println("timeout res code: " + s.getStatusCode()));
    }

    public void sync() {
        // 同步调用的姿势

        // 需要特别注意，这种是使用姿势，不能在响应一个http请求的线程中执行；
        // 比如这个项目中，可以通过  http://127.0.0.1:8080/test 来调用本类的测试方法；但本方法如果被这种姿势调用，则会抛异常；
        // 如果需要正常测试，可以看一下test下的调用case

        WebClient webClient = WebClient.create("http://127.0.0.1:8080");

        String ans = webClient.get().uri("/get?name=一灰灰").retrieve().bodyToMono(String.class).block();
        System.out.println("block get Mono res: " + ans);


        Map<String, Object> uriVariables = new HashMap<>(4);
        uriVariables.put("p1", "一灰灰");
        uriVariables.put("p2", 19);

        List<String> fans =
                webClient.get().uri("/mget?name={p1}&age={p2}", uriVariables).retrieve().bodyToFlux(String.class)
                        .collectList().block();
        System.out.println("block get Flux res: " + fans);
    }
}
