package com.git.hui.boot.web.rest;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重定向
 * Created by @author yihui in 17:40 19/9/29.
 */
@Controller
@RequestMapping(path = "redirect")
public class RedirectRest {

    @ResponseBody
    @GetMapping(path = "index")
    public String index(HttpServletRequest request) {
        return "重定向访问! " + JSON.toJSONString(request.getParameterMap());
    }

    @GetMapping(path = "r1")
    public String r1() {
        return "redirect:/redirect/index?base=r1";
    }

    @ResponseBody
    @GetMapping(path = "r2")
    public void r2(HttpServletResponse response) throws IOException {
        response.sendRedirect("/redirect/index?base=r2");
    }
}
