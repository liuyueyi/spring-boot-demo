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

    /**
     * 字面量，注意用单引号包裹起来
     * @param key
     * @return
     */
    @Log(key = "'yihuihuiblog'")
    public String hello(String key, HelloService helloService) {
        return key + "_" + helloService.say(new DemoDo().setName(key).setAge(10), "prefix");
    }
}
