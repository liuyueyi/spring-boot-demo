package com.git.hui.boot.jdbc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 10:30 20/1/19.
 */
@Component
public class NotEffectSample {
    @Autowired
    private NotEffectDemo notEffectDemo;

    /**
     * 不生效的几种姿势：
     * - 私有方法上，不生效
     * - 内部调用，不生效
     * - 未抛运行异常，不生效
     * - 子线程处理任务，某个线程执行异常，不生效
     */
    public void testNotEffect() {
        testCall(520, (id) -> notEffectDemo.testCompleException(520));

        testCall(530, (id) -> notEffectDemo.testCall(530));

        testCall(540, (id) -> notEffectDemo.testMultThread(540));

        testCall(550, (id) -> notEffectDemo.testMultThread2(550));
    }

    private void testCall(int id, CallFunc<Integer, Boolean> func) {
        System.out.println("============ 事务不生效case start ========== ");
        notEffectDemo.query("transaction before", id);
        try {
            // 事务可以正常工作
            func.apply(id);
        } catch (Exception e) {
        }
        notEffectDemo.query("transaction end", id);
        System.out.println("============ 事务不生效case end ========== \n");
    }

    @FunctionalInterface
    public interface CallFunc<T, R> {
        R apply(T t) throws Exception;
    }
}
