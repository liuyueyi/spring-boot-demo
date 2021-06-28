package com.git.hui.boot.web.example.rest;

import com.git.hui.boot.web.example.api.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yihui
 * @date 2021/3/5
 */
@RestController
public class DemoRest {
    @Autowired
    private UserApi userApi;

    @GetMapping
    public String call(String name, Integer age) {
        String ans = userApi.updateName(name, age);
        String a2 = userApi.getName(1);
        return ans + " | " + a2;
    }
}
