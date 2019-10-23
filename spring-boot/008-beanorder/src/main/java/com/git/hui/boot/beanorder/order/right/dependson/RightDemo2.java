package com.git.hui.boot.beanorder.order.right.dependson;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:41 19/10/17.
 */
//@Component
public class RightDemo2 {
    private String name = "right demo 2";

    public RightDemo2() {
        System.out.println(name);
    }
}
