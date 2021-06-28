package com.git.hui.demo.prometheus.service;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author yihui
 * @date 2021/4/19
 */
@Service
public class HelloService {
    private Random random = new Random();

    private void trySleep() {
        try {
            Thread.sleep(random.nextInt(100) + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String hello(String name) {
        trySleep();
        return "hello: " + name;
    }

    public String welcome(String name) {
        trySleep();
        return "welcome: " + name;
    }
}
