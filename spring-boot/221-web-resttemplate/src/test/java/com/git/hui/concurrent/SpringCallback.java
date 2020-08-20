package com.git.hui.concurrent;


import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.util.concurrent.SuccessCallback;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * Created by @author yihui in 16:00 20/7/5.
 */
public class SpringCallback {

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
        ListenableFutureTask<Integer> task = new ListenableFutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return calculate(10, 20);
            }
        });

        task.addCallback(new SuccessCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println(
                        "回调结果: " + result + " at: " + LocalDateTime.now() + " thread: " + Thread.currentThread());
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println(
                        "回调异常: at: " + LocalDateTime.now() + " e: " + ex + " thread: " + Thread.currentThread());
            }
        });

        Thread thread = new Thread(task);
        thread.start();

        Thread.sleep(300);
        System.out.println("main after task start at: " + LocalDateTime.now() + " thread: " + Thread.currentThread());
        Thread.sleep(5000);
    }
}
