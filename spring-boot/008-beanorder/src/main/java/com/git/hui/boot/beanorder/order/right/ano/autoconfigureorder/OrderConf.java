package com.git.hui.boot.beanorder.order.right.ano.autoconfigureorder;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 11:09 19/10/23.
 */
@AutoConfigureOrder(10)
@Configuration
public class OrderConf {
    public OrderConf() {
        System.out.println("inner order conf init!!!");
    }
}
