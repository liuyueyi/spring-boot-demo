package com.git.hui.boot.properties.controller;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.properties.config.OtherProperBean;
import com.git.hui.boot.properties.config.ProperBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 14:31 18/9/19.
 */
@RestController
public class DemoController {
    @Autowired
    private Environment environment;
    @Autowired
    private ProperBean properBean;
    @Autowired
    private OtherProperBean otherProperBean;

    @Value("${app.demo.val}")
    private String autoInject;

    @Value("${app.demo.not:dada}")
    private String notExists;

    @Value("${user.name}")
    private String name;

    @GetMapping(path = "show")
    public String show() {
        Map<String, String> result = new HashMap<>(6);
        result.put("properBean", properBean.toString());
        result.put("otherBean", otherProperBean.toString());
        result.put("autoInject", autoInject);
        result.put("env", environment.getProperty("server.port"));
        result.put("not", notExists);
        result.put("name", name);
        return JSON.toJSONString(result);
    }
}
