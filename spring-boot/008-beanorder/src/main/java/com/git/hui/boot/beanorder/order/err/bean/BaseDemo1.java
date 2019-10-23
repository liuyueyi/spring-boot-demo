package com.git.hui.boot.beanorder.order.err.bean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:31 19/10/17.
 */
@Order(4)
@Component
public class BaseDemo1 {
    private String name = "base demo 1";

    public BaseDemo1() {
        System.out.println(name);
    }
}
