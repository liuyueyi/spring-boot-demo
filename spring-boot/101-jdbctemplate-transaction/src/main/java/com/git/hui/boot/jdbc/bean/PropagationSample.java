package com.git.hui.boot.jdbc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 事务隔离级别
 * Created by @author yihui in 11:32 20/2/2.
 */
@Component
public class PropagationSample {
    @Autowired
    private PropagationDemo propagationDemo;
    @Autowired
    private PropagationDemo2 propagationDemo2;

    public void testPropagation() {
        testRequired();

        testSupport();
        testMandatory();
        testNotSupport();
        testNever();
        testNested();
    }

    private void testRequired() {
        int id = 420;
        call("Required事务运行", id, propagationDemo::required);
    }

    private void testSupport() {
        int id = 430;
        // 非事务方式，异常不会回滚
        call("support无事务运行", id, propagationDemo::support);

        // 事务运行
        id = 440;
        call("support事务运行", id, propagationDemo2::support);
    }

    private void testMandatory() {
        int id = 450;
        // 非事务方式，抛异常，这个必须在一个事务内部执行
        call("mandatory非事务运行", id, propagationDemo::mandatory);
    }


    private void testNotSupport() {
        int id = 460;
        call("notSupport", id, propagationDemo2::notSupport);
    }


    private void testNever() {
        int id = 470;
        call("never非事务", id, propagationDemo2::never);
    }

    private void testNested() {
        int id = 480;
        call("nested事务", id, propagationDemo2::nested);

        id = 490;
        call("nested事务2", id, propagationDemo2::nested2);
    }

    private void call(String tag, int id, CallFunc<Integer> func) {
        System.out.println("============ " + tag + " start ========== ");
        propagationDemo.query(tag, id);
        try {
            func.apply(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        propagationDemo.query(tag, id);
        System.out.println("============ " + tag + " end ========== \n");
    }


    @FunctionalInterface
    public interface CallFunc<T> {
        void apply(T t) throws Exception;
    }
}
