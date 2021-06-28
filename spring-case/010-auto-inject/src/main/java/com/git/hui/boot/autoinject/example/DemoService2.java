package com.git.hui.boot.autoinject.example;

import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 2021/2/9
 */
@Component
public class DemoService2 {

    public int calculate(int a, int b) {
        doBefore();
        return a + b;
    }

    private void doBefore() {
        System.out.println("-------- inner ----------: " + Thread.currentThread());
    }

}
