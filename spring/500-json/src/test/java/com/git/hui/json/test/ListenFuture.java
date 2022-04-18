package com.git.hui.json.test;

import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yihui
 * @date 21/10/27
 */
public class ListenFuture {
    AtomicInteger atomicInteger = new AtomicInteger(1);

    @Test
    public void testCall() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

        ListenableFuture<Integer> listenableFuture = listeningExecutorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep((long) (100 * Math.random()));
                System.out.printf("执行:" + Thread.currentThread().getName());
                return atomicInteger.addAndGet(1);
            }
        });

        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                System.out.println("执行成功: " + integer);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("执行失败: " + throwable);
            }
        }, listeningExecutorService);

        System.out.println("------- do do do ---------");
        Thread.sleep(10000L);
        System.out.println("------- over ---------");
    }

}
