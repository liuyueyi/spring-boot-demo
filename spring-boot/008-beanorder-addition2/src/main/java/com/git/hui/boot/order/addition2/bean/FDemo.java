package com.git.hui.boot.order.addition2.bean;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 17:31 19/10/22.
 */
@DependsOn("HDemo")
@Component
public class FDemo {
    private String name = "F demo";

    public FDemo() {
        System.out.println(name);
    }
}
