package com.git.hui.web.resin.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yihui
 * @date 21/1/26
 */
@Controller
public class IndexController {

    @GetMapping(path = {"", "/", "/index"})
    public String index() {
        return "index.html";
    }

    @GetMapping(path = "hello")
    @ResponseBody
    public String hello(String name) {
        return "hello " + name;
    }
}
