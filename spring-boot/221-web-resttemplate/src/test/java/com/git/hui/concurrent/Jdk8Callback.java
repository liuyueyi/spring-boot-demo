package com.git.hui.concurrent;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Created by @author yihui in 09:27 20/7/7.
 */
public class Jdk8Callback {

    public static int calculate(int x, int y) {
        System.out.println("calculate begin at: " + LocalDateTime.now() + " thread: " + Thread.currentThread());
        int ans = (x + y) / 2;
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("calculate return at: " + LocalDateTime.now() + " thread: " + Thread.currentThread());
        return ans;
    }

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calculate(10, 20));
        future.whenComplete((a, t) -> {
            if (t == null) {
                System.out.println("CompletableFuture 回调 return: " + a + " at: " + LocalDateTime.now() + " thread: " +
                        Thread.currentThread());
            } else {
                System.out.println("CompletableFuture error at: " + LocalDateTime.now() + " e: " + t + " thread: " +
                        Thread.currentThread());
            }
        });
        Thread.sleep(300);
        System.out.println("main after task start at: " + LocalDateTime.now() + " thread: " + Thread.currentThread());
        Thread.sleep(5000);


        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //                int ans = calculate(10, 20);
                //                // 如果线程内出现异常，这一步没有走到，那么completableFuture后面绑定的事件将不会发生；如果调用 completableFutuer#get方法，则永远阻塞
                //                completableFuture.complete(ans);

                int ans;
                try {
                    ans = calculate(10, 20);
                } catch (Exception e) {
                    completableFuture.completeExceptionally(e);
                    return;
                }

                completableFuture.complete(ans);
            }
        }).start();
        completableFuture.whenComplete((a, t) -> {
            if (t == null) {
                System.out.println("CompletableFuture 回调 return: " + a + " at: " + LocalDateTime.now() + " thread: " +
                        Thread.currentThread());
            } else {
                System.out.println("CompletableFuture error at: " + LocalDateTime.now() + " e: " + t + " thread: " +
                        Thread.currentThread());
            }
        });
        Thread.sleep(300);
        System.out.println("main after task start at: " + LocalDateTime.now() + " thread: " + Thread.currentThread());
        Thread.sleep(5000);
    }
}
