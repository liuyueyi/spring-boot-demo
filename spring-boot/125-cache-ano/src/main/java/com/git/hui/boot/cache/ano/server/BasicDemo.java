package com.git.hui.boot.cache.ano.server;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author yihui
 * @date 2021/2/22
 */
@Service
public class       BasicDemo {
    /**
     * 首先从缓存中查，查到之后，直接返回缓存数据；否则执行方法，并将结果缓存
     * <p>
     * redisKey: cacheNames + key 组合而成 --> 支持SpEL
     * redisValue: 返回结果
     *
     * @param name
     * @return
     */
    @Cacheable(cacheNames = "say", key = "'p_'+ #name")
    public String sayHello(String name) {
        return "hello+" + name + "-->" + UUID.randomUUID().toString();
    }

    /**
     * 失效缓存
     *
     * @param name
     * @return
     */
    @CacheEvict(cacheNames = "say", key = "'p_'+ #name")
    public String evict(String name) {
        return "evict+" + name + "-->" + UUID.randomUUID().toString();
    }

    /**
     * 满足condition条件的才写入缓存
     *
     * @param age
     * @return
     */
    @Cacheable(cacheNames = "condition", key = "#age", condition = "#age % 2 == 0")
    public String setByCondition(int age) {
        return "condition:" + age + "-->" + UUID.randomUUID().toString();
    }

    /**
     * unless, 不满足条件才写入缓存
     *
     * @param age
     * @return
     */
    @Cacheable(cacheNames = "unless", key = "#age", unless = "#age % 2 == 0")
    public String setUnless(int age) {
        return "unless:" + age + "-->" + UUID.randomUUID().toString();
    }


    /**
     * 用于测试异常时，是否会写入缓存
     *
     * @param age
     * @return
     */
    @Cacheable(cacheNames = "exception", key = "#age")
    @CacheEvict(cacheNames = "say", key = "'p_yihuihui'")
    public int exception(int age) {
        return 10 / age;
    }


    /**
     * 注解主要采用代理方式实现，内部调用会导致并不会走缓存
     *
     * @param age
     * @return
     */
    public String innerCall(int age) {
        return setByCondition(age);
    }

    /**
     * 不管缓存有没有，都写入缓存
     *
     * @param age
     * @return
     */
    @CachePut(cacheNames = "t4", key = "#age")
    public String cachePut(int age) {
        return "t4:" + age + "-->" + UUID.randomUUID().toString();
    }


    /**
     * caching实现组合，添加缓存，并失效其他的缓存
     *
     * @param age
     * @return
     */
    @Caching(cacheable = @Cacheable(cacheNames = "caching", key = "#age"), evict = @CacheEvict(cacheNames = "t4", key = "#age"))
    public String caching(int age) {
        return "caching: " + age + "-->" + UUID.randomUUID().toString();
    }
}
