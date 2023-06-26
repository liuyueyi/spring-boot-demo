package com.git.hui.boot.selfconfig.rest;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.selfconfig.auto.SelfConfigFactory;
import com.git.hui.boot.selfconfig.property.SelfConfigContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YiHui
 * @date 2023/6/20
 */
@Slf4j
@RestController
public class IndexController {
    @Autowired
    private MyConfig myConfig;
    @Autowired
    private SelfConfigFactory selfConfigFactory;


    @Value("${config.type:-1}")
    private Integer type;
    @Value("${config.wechat:默认}")
    private String wechat;

    private String email;

    @Value("${config.email:default@email}")
    public IndexController setEmail(String email) {
        this.email = email;
        return this;
    }

    @GetMapping(path = "/")
    public String hello() {
        log.info("innerType: {}, wechat: {}, email: {}", type, wechat, email);
        return JSON.toJSONString(myConfig);
    }

    @GetMapping(path = "update")
    public String updateCache(String key, String val) {
        selfConfigFactory.refreshConfig(key, val);
        SelfConfigContext.getInstance().updateConfig(key, val);
        return "update!";
    }

}
