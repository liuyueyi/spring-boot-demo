package com.git.hui.boot.conditionbean.example.properties;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 10:05 18/10/18.
 */
@RestController
@RequestMapping(path = "property")
public class PropertyRest {

    @Autowired(required = false)
    private PropertyExistBean propertyExistBean;
    @Autowired(required = false)
    private PropertyNotExistBean propertyNotExistBean;
    @Autowired(required = false)
    private PropertyValueExistBean propertyValueExistBean;
    @Autowired(required = false)
    private PropertyValueNotExistBean propertyValueNotExistBean;

    @GetMapping(path = "show")
    public String show() {
        Map<String, String> result = new HashMap<>(4);
        // 存在
        result.put("propertyExistBean", propertyExistBean == null ? "null ===> false" : propertyExistBean.getName());
        // 存在
        result.put("propertyNotExistBean",
                propertyNotExistBean == null ? "null ===> false" : propertyNotExistBean.getName());
        // 存在
        result.put("propertyValueExistBean",
                propertyValueExistBean == null ? "null ==> false" : propertyValueExistBean.getName());
        // 不存在
        result.put("propertyValueNotExistBean",
                propertyValueNotExistBean == null ? "null ==> true" : propertyValueNotExistBean.getName());
        return JSONObject.toJSONString(result);
    }
}
