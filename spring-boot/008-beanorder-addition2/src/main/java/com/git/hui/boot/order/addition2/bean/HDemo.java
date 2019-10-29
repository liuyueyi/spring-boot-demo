package com.git.hui.boot.order.addition2.bean;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 17:34 19/10/22.
 */
@Component
public class HDemo {
    private String name = "H demo";

    public HDemo() {
        System.out.println(name);
    }
}
