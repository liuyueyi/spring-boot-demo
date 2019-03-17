package com.git.hui.spring.rest;

import com.git.hui.spring.server.PrintServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by @author yihui in 18:09 19/3/15.
 */
@RestController
public class HelloRest {
    @Autowired
    private PrintServer printServer;

    @GetMapping(path = "hello", produces="text/html;charset=UTF-8")
    public String sayHello(HttpServletRequest request) {
        printServer.print();
        return "hello, " + request.getParameter("name");
    }


    @GetMapping({"/", ""})
    public String index() {
        return UUID.randomUUID().toString();
    }
}
