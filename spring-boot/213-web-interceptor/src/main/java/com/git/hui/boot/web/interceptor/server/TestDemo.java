package com.git.hui.boot.web.interceptor.server;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author yihui
 * @date 2021/11/18
 */
@Component
public class TestDemo {
    public String showCase() {
        return UUID.randomUUID().toString();
    }

    public String testCase() {
        return "test-" + Math.random();
    }
}
