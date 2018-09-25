package com.git.hui.cloud.config.server.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 22:12 18/9/6.
 */
public class ADemo implements Ordered {
    public ADemo() {
        System.out.println("ADemo !");
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
