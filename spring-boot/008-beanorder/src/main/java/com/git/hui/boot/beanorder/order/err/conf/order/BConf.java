package com.git.hui.boot.beanorder.order.err.conf.order;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by @author yihui in 18:01 19/10/12.
 */
@Order(0)
@Configuration
public class BConf {

    public BConf() {
        System.out.println("BConf init");
    }
}
