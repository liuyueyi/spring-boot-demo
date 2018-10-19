package com.git.hui.boot.dynamicbean.example;

import com.git.hui.boot.dynamicbean.example.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 17:12 18/10/16.
 */
@RestController
@RequestMapping(path = "mock")
public class MockRest {

    @Autowired
    private IUserService userService;

    @GetMapping(path = "id")
    public String getId(@RequestParam String name) {
        return userService.getUserId(name).toString();
    }

}
