package com.git.hui.boot.spel.aop.service;

import com.git.hui.boot.spel.aop.aop.Log;
import org.springframework.stereotype.Service;

/**
 * @author yihui
 * @date 21/6/4
 */
@Service
public class HelloService {

    @Log(key = "#demo.getName()")
    public String say(DemoDo demo, String prefix) {
        return prefix + ":" + demo;
    }
}
