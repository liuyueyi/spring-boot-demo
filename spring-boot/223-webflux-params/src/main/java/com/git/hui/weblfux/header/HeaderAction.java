package com.git.hui.weblfux.header;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-ann-modelattrib-method-args
 *
 * Created by @author yihui in 11:52 20/8/26.
 */
@RestController
@RequestMapping(path = "header")
public class HeaderAction {

    /**
     * 只有请求头包含 myheader 且值为 myvalue的才可以访问到
     *
     * - 正常访问: curl 'http://127.0.0.1:8080/header/filter/yihhui' -H 'myheader: myvalue'
     * - 异常访问: curl 'http://127.0.0.1:8080/header/filter/yihhui' -H 'myheader: myvalue2'  因为请求头不匹配，404
     *
     * @param name
     * @return
     */
    @GetMapping(path = "/filter/{name}", headers = "myheader=myvalue")
    public Mono<String> headerFilter(@PathVariable(name = "name") String name) {
        return Mono.just("request filter: " + name);
    }


    /**
     * 获取请求头
     *
     * curl 'http://127.0.0.1:8080/header/get' -H 'myheader: myvalue' -H 'user-agent: xxxxxxx'
     *
     * @param header
     * @param userAgent
     * @return
     */
    @GetMapping(path = "get")
    public Mono<String> getHeader(@RequestHeader("myheader") String header,
            @RequestHeader("user-agent") String userAgent) {
        return Mono.just("request headers: myheader=" + header + " userAgent=" + userAgent);
    }


    /**
     * 获取cookie
     *
     * curl 'http://127.0.0.1:8080/header/cookie' --cookie 'tid=12343123;tt=abc123def'
     *
     * @param tid
     * @return
     */
    @GetMapping(path = "cookie")
    public Mono<String> getCookie(@CookieValue("tid") String tid) {
        return Mono.just("request cookies tid=" + tid);
    }
}
