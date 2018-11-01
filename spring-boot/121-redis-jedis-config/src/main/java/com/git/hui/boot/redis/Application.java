package com.git.hui.boot.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by @author yihui in 18:13 18/10/30.
 */
@SpringBootApplication
public class Application {

    public Application(RedisTemplate<String, String> redisTemplate) {
        redisTemplate.opsForValue().get("key");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
