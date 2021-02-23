package com.git.hui.boot.cache.ano.server;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author wuzebang
 * @date 2021/2/22
 */
@Service
public class HelloService {

    @Cacheable(value = "say", key = "'p_'+ #name")
    public String sayHello(String name) {
        return "hello+" + name;
    }

}
