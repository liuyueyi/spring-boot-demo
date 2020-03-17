package com.git.hui.boot.order;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 11:49 20/2/28.
 */
@Component
public class DemoBean {
    public DemoBean() {
        System.out.println("demo bean init!");
    }

    public void print() {
        System.out.println("print demo bean ");
    }
}
