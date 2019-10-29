package com.git.hui.boot.beanorder.order.right.constract;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 17:37 19/10/29.
 */
@Component
public class CDemo2 {

    private String name = "cdemo 2";

    public CDemo2() {
        System.out.println(name);
    }
}
