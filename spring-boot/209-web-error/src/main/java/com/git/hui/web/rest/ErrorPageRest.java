package com.git.hui.web.rest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by @author yihui in 16:56 19/9/30.
 */
@Controller
@RequestMapping(path = "page")
public class ErrorPageRest {

    @ResponseBody
    @GetMapping(path = "divide")
    public int divide(int sub) {
        return 1000 / sub;
    }

    private int[] ans = new int[]{1, 2, 3, 4};

    @ResponseBody
    @GetMapping(path = "ary")
    public int ary(int index) {
        return ans[index];
    }

    @ResponseBody
    @GetMapping(path = "run")
    public int runError(int index) {
        if (index / 2 == 0) {
            throw new RuntimeException();
        }

        return index;
    }

    @ResponseBody
    @PostMapping(path = "post")
    public String res(@RequestBody String response, HttpServletRequest httpServletRequest) {
        System.out.println(response);
        return response;
    }


    @ResponseBody
    @PostMapping(path = "get")
    public String get(@RequestParam(name = "key") String key) {
        System.out.println(key);
        return key;
    }
}
