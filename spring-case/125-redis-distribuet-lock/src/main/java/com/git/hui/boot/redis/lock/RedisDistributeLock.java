package com.git.hui.boot.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by @author yihui in 18:17 20/10/19.
 */
@Component
public class RedisDistributeLock {
    public static final String ERROR_CODE = "error";
    @Autowired
    private RedisTemplate redisTemplate;

    private Random random;

    public RedisDistributeLock() {
        random = new Random();
    }

    private String randPrefix() {
        return String.format("%04d", random.nextInt(10000));
    }

    public String tryLock(String lockKey, long expireSeconds, int maxRetryTime) {
        // 为了避免value冲突，加一个随机的前缀串
        String value = randPrefix() + "_" + (System.currentTimeMillis() + expireSeconds * 1000 + 1);
        boolean ans;
        int retryTimes = 0;
        do {
            ans = redisTemplate.opsForValue().setIfAbsent(lockKey, value, expireSeconds, TimeUnit.SECONDS);
            if (ans) {
                return value;
            }

            retryTimes++;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (retryTimes < maxRetryTime);

        return ERROR_CODE;
    }

    public boolean release(String lockKey, String value) {
        //释放锁的lua脚本,保证判断和删除操作的原子性
        String script =
                "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        @SuppressWarnings("unchecked")
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        return (boolean) redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value);
    }
}
