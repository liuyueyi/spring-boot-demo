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
 * 注意，这里选中的 cacheManager = customCacheManager， 而这个缓存管理器，只定义了一个 cacheName = customCache 的缓存
 * 所以下面的cacheName 不能和 AnoCacheService2 中的一样，随便选择前缀；这里必须是 customCacheManager 中声明的 Cache name
 *
 * @author YiHui
 * @date 2023/3/5
 */
@Service
@CacheConfig(cacheNames = "customCache", cacheManager = "customCacheManager")
public class AnoCacheService {

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
