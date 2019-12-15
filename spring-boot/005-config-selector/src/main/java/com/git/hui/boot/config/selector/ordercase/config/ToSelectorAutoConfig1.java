package com.git.hui.boot.config.selector.ordercase.config;

import com.git.hui.boot.config.selector.ordercase.bean.DemoA;
import com.git.hui.boot.config.selector.ordercase.bean.DemoC;
import org.springframework.context.annotation.Bean;

/**
 * Created by @author yihui in 18:16 19/12/13.
 */
public class ToSelectorAutoConfig1 {

    @Bean
    public DemoA demoA() {
        return new DemoA();
    }

    @Bean
    public DemoC demoC() {
        return new DemoC();
    }
}
