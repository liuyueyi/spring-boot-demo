package com.git.hui.boot.properties.value.service;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yihui
 * @date 2021/6/7
 */
@Service
public class RandomService {
    private AtomicInteger cnt = new AtomicInteger(1);

    public String randUid() {
        return cnt.getAndAdd(1) + "_" + UUID.randomUUID().toString();
    }

}
