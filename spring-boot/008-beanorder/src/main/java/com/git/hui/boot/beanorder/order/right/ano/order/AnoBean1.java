package com.git.hui.boot.beanorder.order.right.ano.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 10:58 19/10/23.
 */
@Order(2)
@Component
public class AnoBean1 implements IBean {

    private String name = "ano order bean 1";

    public AnoBean1() {
        System.out.println(name);
    }
}
