package com.git.hui.boot.web.rest;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.web.demo.PrintServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by @author yihui in 22:40 19/3/17.
 */
@RestController
public class HelloRest {
    @Autowired
    private PrintServer printServer;

    @GetMapping(path = "hello")
    public String sayHello(HttpServletRequest request) {
        printServer.print();
        return "hello, " + request.getParameter("name");
    }


    @GetMapping({"/", ""})
    public String index() {
        return UUID.randomUUID().toString();
    }


    @GetMapping("bigReq")
    public String bigReqList() {
        List<String> result = new ArrayList<>(2048);
        for (int i = 0; i < 2048; i++) {
            result.add(UUID.randomUUID().toString());
        }
        return JSON.toJSONString(result);
    }
}
