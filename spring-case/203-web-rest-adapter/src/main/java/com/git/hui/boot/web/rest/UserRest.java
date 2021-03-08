package com.git.hui.boot.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 12:38 20/4/5.
 */
@RestController
@RequestMapping(path = "UserApi")
public class UserRest implements UserApi {
    @Override
    public String getName(int userId) {
        return "一灰灰blog: " + userId;
    }

    @Override
    @PostMapping(path = "updateName")
    public String updateName(String user, int age) {
        return "update " + user + " age to: " + age;
    }
}
