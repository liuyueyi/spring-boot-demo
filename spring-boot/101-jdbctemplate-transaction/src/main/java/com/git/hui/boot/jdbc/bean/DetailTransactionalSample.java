package com.git.hui.boot.jdbc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 10:42 20/1/19.
 */
@Component
public class DetailTransactionalSample {
    @Autowired
    private DetailDemo detailDemo;

    public void testIsolation() throws InterruptedException {
        this.testRuIsolation();
        Thread.sleep(2000);

        this.testRcIsolation();
        Thread.sleep(2000);

        this.testReadOnlyCase();
        Thread.sleep(3000);

        this.testSerializeIsolation();
        Thread.sleep(2000);
    }

    /**
     * rr
     * 测试只读事务
     */
    private void testReadOnlyCase() throws InterruptedException {
        // 子线程开启只读事务，主线程执行修改
        int id = 320;
        new Thread(new Runnable() {
            @Override
            public void run() {
                call("rr 只读事务 - read", id, detailDemo::readRrTransaction);
            }
        }).start();

        Thread.sleep(1000);

        call("rr 读写事务", id, detailDemo::rrTransaction);
    }


    /**
     * ru 隔离级别
     */
    private void testRuIsolation() throws InterruptedException {
        int id = 330;
        new Thread(new Runnable() {
            @Override
            public void run() {
                call("ru: 只读事务 - read", id, detailDemo::readRuTransaction);
            }
        }).start();

        call("ru 读写事务", id, detailDemo::ruTransaction);
    }

    /**
     * rc 隔离级别
     */
    private void testRcIsolation() throws InterruptedException {
        int id = 340;
        new Thread(new Runnable() {
            @Override
            public void run() {
                call("rc: 只读事务 - read", id, detailDemo::readRcTransaction);
            }
        }).start();

        Thread.sleep(1000);

        call("rc 读写事务 - read", id, detailDemo::rcTranaction);
    }


    /**
     * Serialize 隔离级别
     */
    private void testSerializeIsolation() throws InterruptedException {
        int id = 350;
        new Thread(new Runnable() {
            @Override
            public void run() {
                call("Serialize: 只读事务 - read", id, detailDemo::readSerializeTransaction);
            }
        }).start();

        Thread.sleep(1000);

        call("Serialize 读写事务 - read", id, detailDemo::serializeTransaction);
    }


    private void call(String tag, int id, CallFunc<Integer, Boolean> func) {
        System.out.println("============ " + tag + " start ========== ");
        try {
            func.apply(id);
        } catch (Exception e) {
        }
        System.out.println("============ " + tag + " end ========== \n");
    }


    @FunctionalInterface
    public interface CallFunc<T, R> {
        R apply(T t) throws Exception;
    }
}
