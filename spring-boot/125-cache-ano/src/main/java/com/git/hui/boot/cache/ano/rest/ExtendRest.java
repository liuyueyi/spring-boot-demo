package com.git.hui.boot.cache.ano.rest;

import com.git.hui.boot.cache.ano.server.ExtendDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wuzebang
 * @date 2021/3/12
 */
@RestController
@RequestMapping(path = "extend")
public class ExtendRest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ExtendDemo extendDemo;

    @GetMapping(path = "default")
    public Map<String, Object> key(int id) {
        Map<String, Object> res = new HashMap<>();
        res.put("key0", extendDemo.key0());
        res.put("key1", extendDemo.key1(id));
        res.put("key2", extendDemo.key2(id, id));
        res.put("key3", extendDemo.key3(res));

        Set<String> keys = (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<byte[]> sets = connection.keys("k*".getBytes());
            Set<String> ans = new HashSet<>();
            for (byte[] b : sets) {
                ans.add(new String(b));
            }
            return ans;
        });

        res.put("keys", keys);
        return res;
    }

    @GetMapping(path = "self")
    public String self(int id) {
        return extendDemo.selfKey(id);
    }
}
