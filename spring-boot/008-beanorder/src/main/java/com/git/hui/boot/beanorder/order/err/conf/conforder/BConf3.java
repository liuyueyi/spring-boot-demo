package com.git.hui.boot.beanorder.order.err.conf.conforder;

import com.git.hui.boot.beanorder.order.err.conf.conforder.sub.DemoB;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 18:02 19/10/12.
 */
@Configuration
@AutoConfigureOrder(-1)
public class BConf3 {

    @Bean
    public DemoB demoB() {
        return new DemoB();
    }
}
