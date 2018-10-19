package com.git.hui.boot.conditionbean.example.expression;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 10:42 18/10/18.
 */
@RestController
@RequestMapping(path = "express")
public class ExpressRest {
    @Autowired(required = false)
    private ExpressTrueBean expressTrueBean;
    @Autowired(required = false)
    private ExpressFalseBean expressFalseBean;

    @GetMapping(path = "show")
    public String show() {
        Map<String, String> result = new HashMap<>(4);
        result.put("expressTrueBean", expressTrueBean == null ? "null ==> false" : expressTrueBean.getName());
        result.put("expressFalseBean", expressFalseBean == null ? "null ==> true": expressFalseBean.getName());
        return JSONObject.toJSONString(result);
    }
}
