package com.git.hui.boot.config.selector.ordercase.config;

import com.git.hui.boot.config.selector.ordercase.bean.DemoB;
import com.git.hui.boot.config.selector.ordercase.bean.DemoD;
import org.springframework.context.annotation.Bean;

/**
 * Created by @author yihui in 18:16 19/12/13.
 */
public class ToSelectorAutoConfig2 {

    @Bean
    public DemoB demoB() {
        return new DemoB();
    }

    @Bean
    public DemoD demoD() {
        return new DemoD();
    }
}
