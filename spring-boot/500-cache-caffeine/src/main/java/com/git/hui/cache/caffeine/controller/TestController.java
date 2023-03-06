package com.git.hui.cache.caffeine.controller;

import com.git.hui.cache.caffeine.model.User;
import com.git.hui.cache.caffeine.service.AnoCacheService;
import com.git.hui.cache.caffeine.service.AnoCacheService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YiHui
 * @date 2023/3/6
 */
@RestController
public class TestController {

    @Autowired
    private AnoCacheService anoCacheService;

    @Autowired
    private AnoCacheService2 anoCacheService2;

    private AtomicInteger uid = new AtomicInteger(1);
    private AtomicInteger uid2 = new AtomicInteger(1);

    @RequestMapping(path = "save")
    public User save(String name,
                     @RequestParam(required = false, defaultValue = "1") Integer type) {
        if (type == 1) {
            return anoCacheService.saveUser(new User(uid.getAndAdd(1), name));
        } else {
            return anoCacheService2.saveUser(new User(uid2.getAndAdd(1), name));
        }
    }

    @RequestMapping(path = "query")
    public User query(int userId, @RequestParam(required = false, defaultValue = "1") Integer type) {
        User user = type == 1 ? anoCacheService.getUser(userId) : anoCacheService2.getUser(userId);
        return user == null ? new User() : user;
    }

    @RequestMapping(path = "remove")
    public String remove(int userId, @RequestParam(required = false, defaultValue = "1") Integer type) {
        if (type == 1) anoCacheService.removeUser(userId);
        else anoCacheService2.removeUser(userId);
        return "ok";
    }

}
