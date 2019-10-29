package com.git.hui.boot.beanorder.order.right.dependson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by @author yihui in 16:41 19/10/17.
 */
@Component
public class RightDemo2 {
    private String name = "right demo 2";

    @Autowired
    private RightDemo1 rightDemo1;

    public RightDemo2() {
        System.out.println(name);
    }

    @PostConstruct
    public void init() {
        System.out.println(name + " _init");
    }
}
