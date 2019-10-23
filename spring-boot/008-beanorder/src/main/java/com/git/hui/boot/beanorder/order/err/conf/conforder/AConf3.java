package com.git.hui.boot.beanorder.order.err.conf.conforder;

import com.git.hui.boot.beanorder.order.err.conf.conforder.sub.DemoA;
import com.git.hui.boot.beanorder.order.err.conf.conforder.sub.DemoC;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 18:02 19/10/12.
 */
@Configuration
@AutoConfigureOrder(1)
public class AConf3 {

    @Bean
    public DemoA demoA() {
        return new DemoA();
    }

    @Bean
    public DemoC demoC() {
        return new DemoC();
    }

}
