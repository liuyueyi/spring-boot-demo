package com.git.hui.boot.redisson.demo;

import io.micrometer.core.instrument.util.NamedThreadFactory;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @author yihui
 * @date 2021/3/1
 */
@Service
public class IllegalLockSample {
    @Autowired
    private RedissonClient redissonClient;
    
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("schedule-"));
    // 固定线程池，且线程不会被回收，导致时而能重入获取锁，时而不行
    ExecutorService executorService = Executors.newFixedThreadPool(2, new NamedThreadFactory("fixed-"));

    // 普通线程池，空闲线程会被回收，这样就会导致将不会有其他业务方能获取到锁
    ExecutorService customExecutorService = new ThreadPoolExecutor(0, 1,
            1L, TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("custom-"));

    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("threadId schedule: " + Thread.currentThread().getId());
                    lockReIn(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

        testLock();
    }

    private void lockReIn(int id) {
        RLock rLock = redissonClient.getLock("lock_prefix_" + id);
        if (rLock.tryLock()) {
            try {
                System.out.println("------- 执行业务逻辑 --------" + Thread.currentThread());
                Thread.sleep(35000);
                System.out.println("------- 执行完毕 ----------" + Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 模拟释放锁失败
                rLock.unlock();
            }
        } else {
            System.out.println("get lock failed for " + Thread.currentThread());
        }
    }

    private void unLockFailed(int id) {
        RLock rLock = redissonClient.getLock("lock_prefix_" + id);
        if (rLock.tryLock()) {
            try {
                System.out.println("------- 执行业务逻辑 " + id + " --------" + Thread.currentThread());
            } finally {
                // 模拟释放锁失败
                System.out.println(1 / 0);
                rLock.unlock();
            }
        } else {
            System.out.println("get lock failed for " + Thread.currentThread());
        }
    }

    public void testLock() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("threadId fix : " + Thread.currentThread().getId());
                    unLockFailed(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        customExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("threadId custom : " + Thread.currentThread().getId());
                    unLockFailed(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
