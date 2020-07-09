package com.git.hui.boo.webclient.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author yihui in 21:35 20/7/8.
 */
//@RestController
public class ServletRest {

    @GetMapping(path = "get")
    public String get(String name, Integer age) {
        return "get name: " + name + " age: " + age;
    }

    @GetMapping(path = "mget")
    public List<String> getList(String name, Integer age) {
        List<String> list = new ArrayList<>(2);
        list.add("req name: " + name);
        list.add("req age: " + age);
        return list;
    }

    @PostMapping(path = "post")
    public String post(String name, Integer age) {
        return "post name: " + name + " age: " + age;
    }


    @PostMapping(path = "body")
    public String body(@RequestBody Body body) {
        return "post body: " + body;
    }
}
