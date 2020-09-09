package com.git.hui.weblfux.params;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * get 请求参数解析
 * Created by @author yihui in 19:57 20/9/7.
 */
@RestController
@RequestMapping(path = "get")
public class GetAction {

    @GetMapping(path = "basic")
    public Mono<String> basic(String name, Integer age) {
        return Mono.just("basic params name=" + name + " age=" + age);
    }

    @GetMapping(path = "reqParam")
    public Mono<String> reqParam(@RequestParam(name = "name") String name, @RequestParam(name = "age") Integer age) {
        return Mono.just("reqParam name=" + name + " age=" + age);
    }

    @GetMapping(path = "bean")
    public Mono<String> bean(UserDO user) {
        return Mono.just("bean = " + user);
    }

    @ModelAttribute
    public String model(@RequestParam(name = "attr", required = false) String attr, Model model) {
        model.addAttribute("attr", "一灰灰：" + attr);
        return attr;
    }

    @GetMapping(path = "reqAttr")
    public Mono<String> reqAttr(@RequestAttribute("attr") String attr) {
        return Mono.just("reqAttr = " + attr);
    }

    @GetMapping(path = "reqAttr2")
    public Mono<String> reqAttr2(@RequestAttribute("attr") String attr, @RequestAttribute(name = "name") String name) {
        return Mono.just("reqAttr = " + attr + " name = " + name);
    }
}