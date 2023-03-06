package com.git.hui.cache.caffeine.service;

import com.git.hui.cache.caffeine.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1. cacheManager 指定具体的缓存管理器
 * 2. cacheName 表示这个缓存前缀
 * 3. 通过CacheConfig 注解进行修饰，表示适用于这个类下的所有公共方法
 *
 * @author YiHui
 * @date 2023/3/5
 */
@Service
@CacheConfig(cacheNames = "ano2", cacheManager = "otherCacheManager")
public class AnoCacheService2 {

    /**
     * 用一个map来模拟存储
     */
    private Map<Integer, User> userDb = new ConcurrentHashMap<>();

    /**
     * 添加数据，并保存到缓存中, 不管缓存中有没有，都会更新缓存
     *
     * @param user
     */
    @CachePut(key = "#user.uid")
    public User saveUser(User user) {
        userDb.put(user.getUid(), user);
        return user;
    }

    /**
     * 优先从缓存中获取数据，若不存在，则从 userDb 中查询，并会将结果写入到缓存中
     *
     * @param userId
     * @return
     */
    @Cacheable(key = "#userId")
    public User getUser(int userId) {
        System.out.println("doGetUser from DB:" + userId);
        return userDb.get(userId);
    }

    @CacheEvict(key = "#userId")
    public void removeUser(int userId) {
        userDb.remove(userId);
    }

}
