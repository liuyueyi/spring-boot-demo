package com.git.hui.boot.beanorder.order.right.ano.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 10:58 19/10/23.
 */
@Order(1)
@Component
public class AnoBean2 implements IBean {

    private String name = "ano order bean 2";

    public AnoBean2() {
        System.out.println(name);
    }
}
