package com.git.hui.boot.web.rest;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数解析过程
 * Created by @author yihui in 09:34 19/2/18.
 */
@RestController
@RequestMapping(path = "req")
public class ReqDeparseRest {

    @RequestMapping(path = "arg")
    public String defaultArg(String user, Integer age) {
        return user + ":" + age;
    }

    @RequestMapping(path = "request")
    public String servletRequest(HttpServletRequest request) {
        return JSON.toJSONString(request.getParameterMap());
    }

    @RequestMapping(path = "annoParam")
    public String annoParam(@RequestParam("user") String user, @RequestParam("age") Integer age) {
        return user + ":" + age;
    }
}
