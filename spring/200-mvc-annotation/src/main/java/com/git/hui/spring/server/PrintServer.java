package com.git.hui.spring.server;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 15:53 19/3/16.
 */
@Component
public class PrintServer {
    public void print() {
        System.out.println(System.currentTimeMillis());
    }
}
