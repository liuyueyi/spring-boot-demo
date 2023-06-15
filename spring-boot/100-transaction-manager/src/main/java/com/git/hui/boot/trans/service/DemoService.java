package com.git.hui.boot.trans.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YiHui
 * @date 2023/6/14
 */
@Slf4j
@Service
public class DemoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ThreadLocal<String> cache = new ThreadLocal<>();
    private AtomicInteger cnt = new AtomicInteger(1);

    @Autowired
    private DemoService demoService;

    @Transactional(rollbackFor = Exception.class)
    public void outTransExecute() throws InterruptedException {
        String name = "out灰灰" + cnt.getAndAdd(1);
        log.info("{}-out写入缓存: {}", Thread.currentThread(), name);
        jdbcTemplate.execute("insert into money (`name`, `money`) values ('" + name + "', " + cnt.get() + ");");

        new Thread(new Runnable() {
            @Override
            public void run() {
                demoService.transExecute(false);
            }
        }).start();

        // 验证外部事务回滚，子线程的事务是否也会回滚（判断两个事务是否是同一个）
        log.info("外部执行完毕!");
        Thread.sleep(2000);
        Assert.isTrue(Math.random() > 0.5, "外部事务回滚，子线程的事务不会回滚");
    }

    /**
     * 事务提交前执行某些操作
     * - 都是在同一个线程中执行，但是注册回调钩子，会在事务提交前执行任务，即后置到这个方法执行完，再然后在相同的线程中执行
     */
    @Transactional(rollbackFor = Exception.class)
    public void transExecute(boolean hasException) {
        String name = "一灰灰" + cnt.getAndAdd(1);
        log.info("{}-外部写入缓存: {}", Thread.currentThread(), name);
        cache.set(name);
        try {
            jdbcTemplate.execute("insert into money (`name`, `money`) values ('" + name + "', " + cnt.get() + ");");
            registryBeforeCommitOrImmediatelyRun(new Runnable() {
                @Override
                public void run() {
                    String last = cache.get();
                    log.info("{}-缓存中的新增数据: {}", Thread.currentThread(), last);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (hasException) {
                        // 让这里概率性失败，从而模拟事务回滚的场景
                        Assert.isTrue(Math.random() > 0.5f, "模拟事务回滚!");
                    }
                }
            });
        } finally {
            registryAfterCompletionOrImmediatelyRun(() -> {
                cache.remove();
                log.info("事务提交/回滚之后，主动释放上下文信息");
            });
        }

        log.info("transExecute 执行完毕!");
    }


    /**
     * 注册事务回调-事务提交前执行，如果没在事务中就立即执行
     *
     * @param runnable
     */
    public static void registryBeforeCommitOrImmediatelyRun(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        // 处于事务中
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            // 等事务提交前执行，发生错误会回滚事务
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    runnable.run();
                }
            });
        } else {
            // 马上执行
            runnable.run();
        }
    }

    public static void registryAfterCompletionOrImmediatelyRun(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        // 处于事务中
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            // 等事务提交或者回滚之后执行
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    TransactionSynchronization.super.afterCommit();
                    runnable.run();
                }
            });
        } else {
            // 马上执行
            runnable.run();
        }
    }
}
