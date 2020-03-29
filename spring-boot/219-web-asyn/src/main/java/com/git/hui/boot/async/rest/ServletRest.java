package com.git.hui.boot.async.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by @author yihui in 09:27 20/3/29.
 */
@RestController
@RequestMapping(path = "servlet")
public class ServletRest {

    @GetMapping(path = "get")
    public void get(HttpServletRequest request) {
        AsyncContext asyncContext = request.startAsync();
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println("操作完成:" + Thread.currentThread().getName());
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                System.out.println("超时返回!!!");
                asyncContext.getResponse().setCharacterEncoding("utf-8");
                asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                asyncContext.getResponse().getWriter().println("超时了！！！!");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                System.out.println("出现了m某些异常");
                asyncEvent.getThrowable().printStackTrace();

                asyncContext.getResponse().setCharacterEncoding("utf-8");
                asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                asyncContext.getResponse().getWriter().println("出现了某些异常哦！！！!");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                System.out.println("开始执行");
            }
        });

        asyncContext.setTimeout(3000L);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Long.parseLong(request.getParameter("sleep")));
                    System.out.println("内部线程：" + Thread.currentThread().getName());
                    asyncContext.getResponse().setCharacterEncoding("utf-8");
                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                    asyncContext.getResponse().getWriter().println("异步返回!");
                    asyncContext.getResponse().getWriter().flush();
                    // 异步完成，释放
                    asyncContext.complete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("主线程over!!! " + Thread.currentThread().getName());
    }
}
