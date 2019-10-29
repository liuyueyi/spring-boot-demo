package com.git.hui.boot.beanorder.order.right.dependson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by @author yihui in 16:41 19/10/17.
 */
@DependsOn("rightDemo2")
@Component
public class RightDemo1 {
    private String name = "right demo 1";

    @Autowired
    private RightDemo2 rightDemo2;

    public RightDemo1() {
        System.out.println(name);
    }

    @PostConstruct
    public void init() {
        System.out.println(name + " _init");
    }
}
