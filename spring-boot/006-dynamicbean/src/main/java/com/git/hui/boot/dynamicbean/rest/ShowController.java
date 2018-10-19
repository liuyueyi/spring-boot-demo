package com.git.hui.boot.dynamicbean.rest;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.boot.dynamicbean.auto.AutoBean;
import com.git.hui.boot.dynamicbean.auto.AutoDIBean;
import com.git.hui.boot.dynamicbean.auto.AutoFacDIBean;
import com.git.hui.boot.dynamicbean.bean.auto.AnoAutoOriginBean;
import com.git.hui.boot.dynamicbean.bean.manual.AnoOriginBean;
import com.git.hui.boot.dynamicbean.manual.ManualBean;
import com.git.hui.boot.dynamicbean.manual.ManualDIBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 15:11 18/10/13.
 */
@RestController
public class ShowController {

    @Autowired
    private ManualBean manualBean;
    @Autowired
    private ManualDIBean manualDIBean;
    @Autowired
    private AnoOriginBean anoOriginBean;

    public ShowController() {
        System.out.println("ShowController init: " + System.currentTimeMillis());
    }

    @GetMapping(path = "show")
    public String show(String msg) {
        Map<String, String> result = new HashMap<>(8);
        result.put("manualBean", manualBean == null ? "null" : manualBean.print(msg));
        result.put("manualDIBean", manualDIBean == null ? "null" : manualDIBean.print(msg));
        result.put("anoOriginBean",anoOriginBean.print());
        return JSONObject.toJSONString(result);
    }


    @Autowired
    private AutoBean autoBean;
    @Autowired
    private AutoDIBean autoDIBean;
    @Autowired
    private AutoFacDIBean autoFacDIBean;
    @Autowired
    private AnoAutoOriginBean anoAutoOriginBean;
    @GetMapping(path = "auto")
    public String autoShow() {
        Map<String, String> result = new HashMap<>(8);
        result.put("autoBean", autoBean == null ? "null" : autoBean.print());
        result.put("manualDIBean", autoDIBean == null ? "null" : autoDIBean.print());
        result.put("autoFacDIBean",autoFacDIBean == null ? "null" : autoFacDIBean.print());
        result.put("anoAutoOriginBean",anoAutoOriginBean.print());
        return JSONObject.toJSONString(result);
    }
}
