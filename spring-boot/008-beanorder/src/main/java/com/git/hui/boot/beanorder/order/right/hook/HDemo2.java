package com.git.hui.boot.beanorder.order.right.hook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 18:10 19/10/29.
 */
@Component
public class HDemo2 {
    private String name = "h demo 2";

//    @Autowired
//    private HDemo3 hDemo3;

    public HDemo2() {
        System.out.println(name);
    }

    public void show() {
//        System.out.println(hDemo3.getName());
    }
}
