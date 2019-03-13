package com.git.hui.boot.aop.demo;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by @author yihui in 18:32 19/3/13.
 */
@Component
public class PrintDemo {

    public String genRand(int seed, String suffix) {
        return seed + UUID.randomUUID().toString() + suffix;
    }
}
