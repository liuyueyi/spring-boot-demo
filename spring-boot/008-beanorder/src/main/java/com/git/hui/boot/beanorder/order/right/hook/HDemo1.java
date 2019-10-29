package com.git.hui.boot.beanorder.order.right.hook;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 18:10 19/10/29.
 */
@Component
public class HDemo1 {
    private String name = "h demo 1";

    public HDemo1() {
        System.out.println(name);
    }
}
