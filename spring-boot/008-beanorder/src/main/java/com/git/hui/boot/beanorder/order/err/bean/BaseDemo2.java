package com.git.hui.boot.beanorder.order.err.bean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:31 19/10/17.
 */
@Order(3)
@Component
public class BaseDemo2 {
    private String name = "base demo 2";

    public BaseDemo2() {
        System.out.println(name);
    }
}
