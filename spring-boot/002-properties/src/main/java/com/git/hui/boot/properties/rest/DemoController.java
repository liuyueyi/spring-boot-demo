package com.git.hui.boot.properties.rest;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.boot.properties.config.BizConfig;
import com.git.hui.boot.properties.config.ValueConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 16:39 18/9/21.
 */
@RestController
public class DemoController {

    @Autowired
    private ContextRefresher contextRefresher;

    @Autowired
    private BizConfig bizConfig;

    @Autowired
    private ValueConfig valueConfig;

    @Value("${rest.uuid}")
    private String uuid;

    @GetMapping(path = "/show")
    public String show() {
        JSONObject res = new JSONObject();
        res.put("biz", JSONObject.toJSONString(bizConfig));
        res.put("uuid", valueConfig.getUuid());
        res.put("no-refresh", uuid);
        return res.toJSONString();
    }

    @GetMapping(path = "/refresh")
    public String refresh() {
        new Thread(() -> contextRefresher.refresh()).start();
        return show();
    }

    @EventListener
    public void envListener(EnvironmentChangeEvent event) {
        System.out.println("conf change: " + event);
    }
}
