package com.git.hui.boot.beanorder.order.right.constract;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 17:37 19/10/29.
 */
@Component
public class CDemo1 {

    private String name = "cdemo 1";

    public CDemo1(CDemo2 cDemo2) {
        System.out.println(name);
    }
}
