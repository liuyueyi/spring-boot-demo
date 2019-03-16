package com.git.hui.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by @author yihui in 19:15 19/3/15.
 */
@RestController
public class HelloRest {

    @Autowired
    private PrintServer printServer;

    @ResponseBody
    @GetMapping("hello")
    public String sayHello(HttpServletRequest request) {
        printServer.print();
        return "hello, " + request.getParameter("name");
    }
}
