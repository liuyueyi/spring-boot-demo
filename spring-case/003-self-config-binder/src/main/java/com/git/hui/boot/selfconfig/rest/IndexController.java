package com.git.hui.boot.selfconfig.rest;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.selfconfig.auto.SelfConfigContainer;
import com.git.hui.boot.selfconfig.property.SelfConfigContext;
import com.git.hui.boot.selfconfig.util.SpringUtil;
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
    private SelfConfigContainer selfConfigContainer;
    @Autowired
    private UserConfig userConfig;


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

    @GetMapping(path = "/user")
    public UserConfig user() {
        return userConfig;
    }

    @GetMapping(path = "update")
    public String updateCache(String key, String val) {
//        selfConfigContainer.refreshConfig(key, val);
        SelfConfigContext.getInstance().updateConfig(key, val);
        return wechat + "_" + type + "_" + email;
//        return JSON.toJSONString(userConfig);
    }

    @GetMapping(path = "get")
    public String getProperty(String key) {
        return SpringUtil.getEnvironment().getProperty(key);
    }
}
