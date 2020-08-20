package com.git.hui.concurrent;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by @author yihui in 15:34 20/7/5.
 */
public class BasicDemo {

    public static int calculate(int x, int y) {
        System.out.println("calculate begin at: " + LocalDateTime.now());
        int ans = (x + y) / 2;
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("calculate return at: " + LocalDateTime.now());
        return ans;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask(new Callable() {
            @Override
            public Integer call() throws Exception {
                return calculate(10, 20);
            }
        });

        Thread thread = new Thread(task);
        thread.start();

        Thread.sleep(300);
        System.out.println("main after task start at: " + LocalDateTime.now());
        int ans = task.get();
        System.out.println("after return: " + ans + " at: " + LocalDateTime.now());

        Thread.sleep(5000);
    }

}
