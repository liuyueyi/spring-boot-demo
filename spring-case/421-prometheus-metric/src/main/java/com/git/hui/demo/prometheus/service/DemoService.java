package com.git.hui.demo.prometheus.service;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author yihui
 * @date 2021/4/19
 */
@Service
public class DemoService {
    private Random random = new Random();

    private void trySleep() {
        try {
            Thread.sleep(random.nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int add(int a, int b) {
        trySleep();
        return a + b;
    }

    public int sub(int a, int b) {
        trySleep();
        return a - b;
    }

    public int divide(int a, int b) {
        trySleep();
        return a / b;
    }
}
