package com.git.hui.boot.async.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

/**
 * Created by @author yihui in 21:32 20/3/28.
 */
@RestController
@RequestMapping(path = "task")
public class WebAysncTaskRest {

    @GetMapping(path = "get")
    public WebAsyncTask<String> get(long sleep, boolean error) {
        Callable<String> callable = () -> {
            System.out.println("do some thing");
            Thread.sleep(sleep);

            if (error) {
                System.out.println("出现异常，返回!!!");
                throw new RuntimeException("异常了!!!");
            }

            return "hello world";
        };

        WebAsyncTask<String> webTask = new WebAsyncTask<>(3000, callable);
        webTask.onCompletion(() -> System.out.println("over!!!"));

        webTask.onTimeout(() -> {
            System.out.println("超时了");
            return "超时返回!!!";
        });

        webTask.onError(() -> {
            System.out.println("出现异常了!!!");
            return "异常返回";
        });

        return webTask;
    }
}
