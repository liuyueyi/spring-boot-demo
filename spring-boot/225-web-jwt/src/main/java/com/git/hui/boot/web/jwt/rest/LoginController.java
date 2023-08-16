package com.git.hui.boot.web.jwt.rest;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author YiHui
 * @date 2023/8/16
 */
@Controller
public class LoginController {
    private Map<String, String> userCache = create("一灰灰", "123", "yihui", "hello");

    private static <K, V> Map<K, V> create(K k, V v, Object... kvs) {
        Map<K, V> map = new HashMap<>(kvs.length + 1);
        map.put(k, v);
        for (int i = 0; i < kvs.length; i += 2) {
            map.put((K) kvs[i], (V) kvs[i + 1]);
        }
        return map;
    }


    @PostMapping(path = "/login")
    @ResponseBody
    public String login(String uname, String pwd, HttpServletResponse response) {
        if (!userCache.containsKey(uname) || !Objects.equals(pwd, userCache.get(uname))) {
            return "用户名密码错误，登录失败";
        }

        String token = JWT.create()
                .withIssuer("一灰灰blog")
                .withExpiresAt(new Date(System.currentTimeMillis() +10L))
                .withPayload(create("uname", uname, "wechat", "https://spring.hhui.top/spring-blog/imgs/info/wx.jpg", "site", "https://spring.hhui.top"))
                .sign(Algorithm.HMAC256("helloWorld"));

        response.addCookie(new Cookie("Authorization", token));
        return token;
    }


    @GetMapping("query")
    @ResponseBody
    public Object queryInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            return "未登录";
        }
        token = token.substring(token.indexOf(" ")).trim();

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("helloWorld")).withIssuer("一灰灰blog").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            HashMap pay = JSONObject.parseObject(new String(Base64Utils.decodeFromString(decodedJWT.getPayload())), HashMap.class);
            pay.put("query", "查询成功!");
            return pay;
        } catch (Exception e) {
            e.printStackTrace();
            return "鉴权失败: " + e.getMessage();
        }
    }

    @GetMapping(path = {"/", "", "/index"})
    public String index() {
        return "index";
    }
}
