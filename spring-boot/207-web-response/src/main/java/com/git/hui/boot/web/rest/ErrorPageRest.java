package com.git.hui.boot.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by @author yihui in 16:56 19/9/30.
 */
@Controller
@RequestMapping(path = "page")
public class ErrorPageRest {

    @ResponseBody
    @GetMapping(path = "divide")
    public int divide(int sub) {
        System.out.println("divide1");
        return 1000 / sub;
    }
}
