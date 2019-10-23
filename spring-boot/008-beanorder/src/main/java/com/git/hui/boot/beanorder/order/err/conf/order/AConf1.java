package com.git.hui.boot.beanorder.order.err.conf.order;

import com.git.hui.boot.beanorder.order.err.conf.order.sub.Demo1;
import com.git.hui.boot.beanorder.order.err.conf.order.sub.Demo3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by @author yihui in 18:32 19/10/22.
 */
@Order(2)
@Configuration
public class AConf1 {

    @Bean
    public Demo1 demo1() {
        return new Demo1();
    }

    @Bean
    public Demo3 demo3() {
        return new Demo3();
    }

}
