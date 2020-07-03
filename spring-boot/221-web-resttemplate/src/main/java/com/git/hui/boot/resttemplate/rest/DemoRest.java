package com.git.hui.boot.resttemplate.rest;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Decoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * Created by @author yihui in 19:33 20/6/16.
 */
@RestController
public class DemoRest {


    private String getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String name;

        JSONObject headers = new JSONObject();
        while (headerNames.hasMoreElements()) {
            name = headerNames.nextElement();
            headers.put(name, request.getHeader(name));
        }
        return headers.toJSONString();
    }

    private String getParams(HttpServletRequest request) {
        return JSONObject.toJSONString(request.getParameterMap());
    }

    private String getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "";
        }

        JSONObject ck = new JSONObject();
        for (Cookie cookie : cookies) {
            ck.put(cookie.getName(), cookie.getValue());
        }
        return ck.toJSONString();
    }

    private String buildResult(HttpServletRequest request) {
        return buildResult(request, null);
    }

    private String buildResult(HttpServletRequest request, Object obj) {
        String params = getParams(request);
        String headers = getHeaders(request);
        String cookies = getCookies(request);

        if (obj != null) {
            params += " | " + obj;
        }

        return "params: " + params + "\nheaders: " + headers + "\ncookies: " + cookies;
    }

    @GetMapping(path = "get")
    public String get(HttpServletRequest request) {
        return buildResult(request);
    }


    @PostMapping(path = "post")
    public String post(HttpServletRequest request) {
        return buildResult(request);
    }

    @Data
    @NoArgsConstructor
    public static class ReqBody implements Serializable {
        private static final long serialVersionUID = -4536744669004135021L;
        private String name;
        private Integer age;
    }

    @PostMapping(path = "body")
    public String postBody(@RequestBody ReqBody body) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return buildResult(request, body);
    }


    @GetMapping(path = "timeout")
    public String timeOut(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(60_000L);
        return buildResult(request);
    }

    /**
     * 标准的http auth验证
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping(path = "auth")
    public String auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String auth = request.getHeader("Authorization");
        if (StringUtils.isEmpty(auth)) {
            response.setStatus(401);
            response.setHeader("WWW-Authenticate", "Basic realm=\"input username and password\"");
            return buildResult(request) + "\n>>>no auth header";
        }

        String[] userAndPass = new String(new BASE64Decoder().decodeBuffer(auth.split(" ")[1])).split(":");
        if (userAndPass.length < 2) {
            response.setStatus(401);
            response.setHeader("WWW-Authenticate", "Basic realm=\"input username and password\"");
            return buildResult(request) + "\n>>>illegal auth: " + auth;
        }

        if ("user".equalsIgnoreCase(userAndPass[0]) && "pwd".equalsIgnoreCase(userAndPass[1])) {
            return buildResult(request) + "\n>>>auth: success!";
        }

        response.setStatus(401);
        response.setHeader("WWW-Authenticate", "Basic realm=\"input username and password\"");
        return buildResult(request) + "\n>>>illegal user or pwd!";
    }


    @GetMapping(path = "proxy")
    private String proxy(HttpServletRequest request) {
        String remote = request.getRemoteHost() + ":" + request.getRemotePort();
        return buildResult(request) + "\n>>>remote ipInfo: " + remote;
    }

    @Autowired
    private RestTemplateDemo restTemplateDemo;

    @GetMapping(path = "test")
    public String test() {
        restTemplateDemo.test();
        return "over";
    }
}
