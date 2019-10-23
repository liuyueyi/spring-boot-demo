package com.git.hui.boot.beanorder.order.right.dependson;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:41 19/10/17.
 */
//@DependsOn("rightDemo2")
//@Component
public class RightDemo1 {
    private String name = "right demo 1";

    public RightDemo1() {
        System.out.println(name);
    }
}
