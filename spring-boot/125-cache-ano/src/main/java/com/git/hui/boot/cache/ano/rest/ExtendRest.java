package com.git.hui.boot.cache.ano.rest;

import com.git.hui.boot.cache.ano.server.ExtendDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuzebang
 * @date 2021/3/12
 */
@RestController
@RequestMapping(path = "extend")
public class ExtendRest {
    @Autowired
    private ExtendDemo extendDemo;

    @GetMapping(path = "default")
    public String key(int id) {
        return extendDemo.key(id);
    }

    @GetMapping(path = "self")
    public String self(int id) {
        return extendDemo.selfKey(id);
    }
}
