package com.git.hui.boot.selfconfig.rest;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.selfconfig.auto.SelfConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YiHui
 * @date 2023/6/20
 */
@RestController
public class IndexController {
    @Autowired
    private MyConfig myConfig;

    @Value("${config.type:-1}")
    private Integer type;

    @Autowired
    private SelfConfigFactory selfConfigFactory;

    @GetMapping(path = "/")
    public String hello() {
        System.out.println("innerType:" + type);
        return JSON.toJSONString(myConfig);
    }

    @GetMapping(path = "update")
    public String updateCache(Integer val) {
        selfConfigFactory.cache.put("type", val);
        return "update!";
    }

}
