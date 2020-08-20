package com.git.hui.concurrent;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by @author yihui in 15:41 20/7/5.
 */
public class SimpleCallback {

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

    @FunctionalInterface
    public interface TaskCallback<T> {
        void execute(@Nullable T var1);
    }


    public static class CallbackFutureTask<V> extends FutureTask<V> {

        private TaskCallback<V> successCallBack;
        private TaskCallback<Throwable> failCallBack;

        public CallbackFutureTask(Callable<V> callable, TaskCallback<V> successCallBackCallBack,
                TaskCallback<Throwable> failCallBack) {
            super(callable);
            this.successCallBack = successCallBackCallBack;
            this.failCallBack = failCallBack;
        }

        /**
         * 重写执行完毕的方法，增加回调处理
         */
        protected void done() {
            Object cause;
            try {
                V result = this.get();
                this.successCallBack.execute(result);
                return;
            } catch (InterruptedException var3) {
                Thread.currentThread().interrupt();
                return;
            } catch (ExecutionException var4) {
                cause = var4.getCause();
                if (cause == null) {
                    cause = var4;
                }
            } catch (Throwable var5) {
                cause = var5;
            }

            this.failCallBack.execute((Throwable) cause);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CallbackFutureTask<Integer> task = new CallbackFutureTask(new Callable() {
            @Override
            public Integer call() throws Exception {
                return calculate(10, 20);
            }
        }, (s) -> System.out
                .println("回调结果: " + s + " at: " + LocalDateTime.now() + " thread: " + Thread.currentThread()),
                (t) -> System.out.println(
                        "回调异常: at: " + LocalDateTime.now() + " e: " + t + " thread: " + Thread.currentThread()));

        Thread thread = new Thread(task);
        thread.start();

        Thread.sleep(300);
        System.out.println("main after task start at: " + LocalDateTime.now() + " thread: " + Thread.currentThread());
        Thread.sleep(5000);

    }

}
