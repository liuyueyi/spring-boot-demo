package com.git.hui.boot.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by @author yihui in 11:10 18/10/26.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
public class Application {

    public Application(@Qualifier("localRedisTemplate") RedisTemplate<String, String> localRedisTemplate,
            @Qualifier("redisTemplate") RedisTemplate<String, String> defaultRedisTemplate)
            throws InterruptedException {
        // 10s的有效时间
        localRedisTemplate.delete("key");
        localRedisTemplate.opsForValue().set("key", "value", 100, TimeUnit.MILLISECONDS);
        String ans = localRedisTemplate.opsForValue().get("key");
        System.out.println("value".equals(ans));
        TimeUnit.MILLISECONDS.sleep(200);
        ans = localRedisTemplate.opsForValue().get("key");
        System.out.println("value".equals(ans) + " >> false ans should be null! ans=[" + ans + "]");


        defaultRedisTemplate.opsForValue().set("key", "value", 100, TimeUnit.MILLISECONDS);
        ans = defaultRedisTemplate.opsForValue().get("key");
        System.out.println(ans);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
