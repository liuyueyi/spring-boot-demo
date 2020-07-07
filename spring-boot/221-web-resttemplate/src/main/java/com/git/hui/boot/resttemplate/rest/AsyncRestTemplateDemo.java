package com.git.hui.boot.resttemplate.rest;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by @author yihui in 13:26 20/7/5.
 */
@Slf4j
@Component
public class AsyncRestTemplateDemo {

    public void test() {
        basic();
        guava();
    }

    public void basic() {
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        long start = System.currentTimeMillis();
        ListenableFuture<ResponseEntity<String>> response =
                asyncRestTemplate.getForEntity("http://127.0.0.1:8080/atimeout?name=一灰灰&age=20", String.class);
        response.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("1. Async get error! cost: {}, e: {}", System.currentTimeMillis() - start, throwable);
            }

            @Override
            public void onSuccess(ResponseEntity<String> stringResponseEntity) {
                String ans = stringResponseEntity.getBody();
                log.info("1. success get: {}, cost: {}", ans, System.currentTimeMillis() - start);
            }
        });

        response = asyncRestTemplate.getForEntity("http://127.0.0.1:8080/4xx?name=一灰灰&age=20", String.class);
        response.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("2. Async get error! cost: {}, e: {}", System.currentTimeMillis() - start, ex);
            }

            @Override
            public void onSuccess(ResponseEntity<String> result) {
                log.info("2. success get: {}, cost: {}", result, System.currentTimeMillis() - start);
            }
        });
        log.info("do something else!!!");
    }

    public void guava() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 基于jdk线程池，创建支持异步回调的线程池
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

        long start = System.currentTimeMillis();
        // 具体的异步访问任务
        com.google.common.util.concurrent.ListenableFuture<HttpEntity<String>> ans =
                listeningExecutorService.submit(new Callable<HttpEntity<String>>() {
                    @Override
                    public HttpEntity<String> call() throws Exception {
                        RestTemplate restTemplate = new RestTemplate();
                        return restTemplate
                                .getForEntity("http://127.0.0.1:8080/atimeout?name=一灰灰&age=19", String.class);
                    }
                });

        // 完成之后，在指定的线程池（第三个参数）中回调
        Futures.addCallback(ans, new com.google.common.util.concurrent.FutureCallback<HttpEntity<String>>() {
            @Override
            public void onSuccess(@Nullable HttpEntity<String> stringHttpEntity) {
                log.info("guava call back res: {}, cost: {}", stringHttpEntity.getBody(),
                        System.currentTimeMillis() - start);
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.info("guava call back failed cost:{}, e: {}", System.currentTimeMillis() - start, throwable);
            }
        }, Executors.newFixedThreadPool(1));

        log.info("do something other in guava!");
        listeningExecutorService.shutdown();
    }
}
