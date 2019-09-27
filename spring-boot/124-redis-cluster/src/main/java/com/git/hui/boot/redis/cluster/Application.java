package com.git.hui.boot.redis.cluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by @author yihui in 17:55 19/9/27.
 */
@SpringBootApplication
public class Application {

    public Application(RedisTemplate redisTemplate) {
        redisTemplate.opsForValue().set("spring-r-cluster-1", 123);
        redisTemplate.opsForValue().set("spring-r-cluster-2", 456);
        redisTemplate.opsForValue().set("spring-r-cluster-3", 789);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
