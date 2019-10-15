package com.git.hui.boot.beanorder.order.conf.error;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 18:02 19/10/12.
 */
@Component
@AutoConfigureOrder(1)
public class AConf2 {
    public AConf2() {
        System.out.println("Ture A Conf init!");
    }
}
