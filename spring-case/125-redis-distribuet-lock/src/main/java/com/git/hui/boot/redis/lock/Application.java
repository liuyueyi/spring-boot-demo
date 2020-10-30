package com.git.hui.boot.redis.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by @author yihui in 18:17 20/10/19.
 */
@SpringBootApplication
public class Application {

    private AtomicInteger count = new AtomicInteger(35);

    /**
     * 在一个线程持有锁的过程中，不允许其他的线程持有锁
     *
     * @param redisDistributeLock
     * @param lockKey
     * @param threadName
     * @param retryTime
     */
    private void threadTest(RedisDistributeLock redisDistributeLock, String lockKey, String threadName, int retryTime, int n) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String value = redisDistributeLock.tryLock(lockKey, 10_000, retryTime);
                if (count.get() >= n) {
                    int left = count.addAndGet(-n);
                    System.out.println(threadName + "减库存，剩余: " + left + " 购买: " + n);
                } else {
                    System.out.println(threadName + "库存不足下单失败，当前库存: " + count.get() + " 购买： " + n);
                }
                redisDistributeLock.release(lockKey, value);
            }
        }).start();
    }

    public Application(RedisDistributeLock redisDistributeLock) throws InterruptedException {
        String lockKey = "lock_key";
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            threadTest(redisDistributeLock, lockKey, "t-" + i, random.nextInt(30), random.nextInt(3) + 1);
        }
        Thread.sleep(20 * 1000);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
