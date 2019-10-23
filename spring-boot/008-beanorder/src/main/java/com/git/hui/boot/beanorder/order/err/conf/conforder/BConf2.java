package com.git.hui.boot.beanorder.order.err.conf.conforder;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 18:02 19/10/12.
 */
@Configuration
@AutoConfigureOrder(-1)
public class BConf2 {
    public BConf2() {
        System.out.println("B conf2 init!");
    }
}
