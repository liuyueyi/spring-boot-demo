package com.git.hui.boot.bean.rest;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.bean.autoload.factory.FacDemoBean;
import com.git.hui.boot.bean.autoload.simple.AnoDemoBean;
import com.git.hui.boot.bean.autoload.simple.ConfigDemoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 17:04 18/9/30.
 */
@RestController
public class DemoController {
    private AnoDemoBean anoDemoBean;

    public DemoController(AnoDemoBean anoDemoBean) {
        this.anoDemoBean = anoDemoBean;
    }

    @Autowired
    private ConfigDemoBean configDemoBean;

    private FacDemoBean facDemoBean;

    @Autowired
    private void setFacDemoBean(FacDemoBean facDemoBean) {
        this.facDemoBean = facDemoBean;
    }


    @GetMapping(path = "/show")
    public String show(String name) {
        Map<String, String> map = new HashMap<>(4);
        map.put("ano", anoDemoBean.getName(name));
        map.put("config", configDemoBean.getName(name));
        map.put("fac", facDemoBean.getName(name));
        return JSON.toJSONString(map);
    }
}
