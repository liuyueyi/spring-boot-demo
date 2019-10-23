package com.git.hui.boot.order.addition2.bean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:28 19/10/17.
 */
@Order(3)
@Component
public class BDemo {
    private String name = "B demo";

    public BDemo() {
//        System.out.println(name);
    }
}
