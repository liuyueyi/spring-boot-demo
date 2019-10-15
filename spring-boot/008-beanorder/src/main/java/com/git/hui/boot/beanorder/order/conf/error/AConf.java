package com.git.hui.boot.beanorder.order.conf.error;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by @author yihui in 18:00 19/10/12.
 */
@Order(1)
@Configuration
public class AConf {

    public AConf() {
        System.out.println("Aconf init!");
    }
}
