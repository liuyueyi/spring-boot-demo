package com.git.hui.boot.cache.ano.server;

import com.alibaba.fastjson.JSON;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

/**
 * @author yihui
 * @date 2021/3/12
 */
@Service
public class ExtendDemo {

    /**
     * 没有指定key时，采用默认策略 {@link org.springframework.cache.interceptor.SimpleKeyGenerator } 生成key
     * <p>
     * 对应的key为: k1::id
     * value --> 等同于 cacheNames
     * @param id
     * @return
     */
    @Cacheable(value = "k1")
    public String key1(int id) {
        return "defaultKey:" + id;
    }

    /**
     * redis_key :  k2::SimpleKey[]
     *
     * @return
     */
    @Cacheable(value = "k0")
    public String key0() {
        return "key0";
    }

    /**
     * redis_key :  k2::SimpleKey[id,id2]
     *
     * @param id
     * @param id2
     * @return
     */
    @Cacheable(value = "k2")
    public String key2(Integer id, Integer id2) {
        return "key1" + id + "_" + id2;
    }


    @Cacheable(value = "k3")
    public String key3(Map map) {
        return "key3" + map;
    }

    /**
     * 对应的redisKey 为： get  vv::ExtendDemo#selfKey([id])
     *
     * @param id
     * @return
     */
    @Cacheable(value = "vv", keyGenerator = "selfKeyGenerate")
    public String selfKey(int id) {
        return "selfKey:" + id + " --> " + UUID.randomUUID().toString();
    }

    @Component("selfKeyGenerate")
    public static class SelfKeyGenerate implements KeyGenerator {
        @Override
        public Object generate(Object target, Method method, Object... params) {
            return target.getClass().getSimpleName() + "#" + method.getName() + "(" + JSON.toJSONString(params) + ")";
        }
    }

/**
 * 通过自定义的RedisCacheManager, 对value进行解析，=后面的表示失效时间
 * @param key
 * @return
 */
@Cacheable(value = "ttl=30")
public String ttl(String key) {
    return "k_" + key;
}
}
