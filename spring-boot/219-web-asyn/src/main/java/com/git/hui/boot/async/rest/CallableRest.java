package com.git.hui.boot.async.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * Created by @author yihui in 21:15 20/3/28.
 */
@RestController
@RequestMapping(path = "call")
public class CallableRest {

    @GetMapping(path = "get")
    public Callable<String> get() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("do some thing");
                Thread.sleep(1000);
                System.out.println("执行完毕，返回!!!");
                return "over!";
            }
        };

        return callable;
    }


    @GetMapping(path = "exception")
    public Callable<String> exception() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("do some thing");
                Thread.sleep(1000);
                System.out.println("出现异常，返回!!!");
                throw new RuntimeException("some error!");
            }
        };

        return callable;
    }
}
