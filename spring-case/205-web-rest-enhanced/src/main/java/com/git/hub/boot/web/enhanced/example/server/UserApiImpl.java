package com.git.hub.boot.web.enhanced.example.server;

import com.git.hub.boot.web.enhanced.example.api.UserApi;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yihui
 * @date 2021/3/9
 */
@Service
public class UserApiImpl implements UserApi {
    Map<String, Integer> cache = new ConcurrentHashMap<>();

    @Override
    public String getName(int id) {
        return "一灰灰blog : " + id;
    }

    @Override
    public String updateName(String user, int age) {
        if (cache.containsKey(user)) {
            Integer old = cache.put(user, age);
            return "update " + user + " old:" + old + " to:" + age;
        }

        cache.put(user, age);
        return "add new: " + user + "| " + age;
    }
}
