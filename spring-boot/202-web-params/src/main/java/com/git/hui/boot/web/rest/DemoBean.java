package com.git.hui.boot.web.rest;

//import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 14:09 19/3/29.
 */
public class DemoBean {

    public String show() {
        Map<String, String> map = new HashMap<>();
        map.put("hello", "world");
//        return JSON.toJSONString(map);
        return "hello";
    }

}
