package com.git.hui.boot.config.selector.ordercase.bean;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 18:15 19/12/13.
 */
@Component
public class DemoE {

    private String name = "demoE";

    public DemoE() {
        System.out.println(name);
    }
}
