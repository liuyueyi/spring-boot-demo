package com.git.hui.boot.beanorder.order.err.conf.order;

import com.git.hui.boot.beanorder.order.err.conf.order.sub.Demo2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by @author yihui in 18:32 19/10/22.
 */
@Order(1)
@Configuration
public class BConf1 {

    @Bean
    public Demo2 demo2() {
        return new Demo2();
    }
}
