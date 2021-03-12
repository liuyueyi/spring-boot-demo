package com.git.hui.boot.cache.ano.server;

import com.alibaba.fastjson.JSON;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author wuzebang
 * @date 2021/3/12
 */
@Service
public class ExtendDemo {

    /**
     * 对应的key为: vv::id
     * @param id
     * @return
     */
    @Cacheable(value = "vv")
    public String key(int id) {
        return "defaultKey:" + id + " --> " + UUID.randomUUID().toString();
    }

    /**
     * 对应的redisKey 为： get vv::ExtendDemo#selfKey([id])
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
}
