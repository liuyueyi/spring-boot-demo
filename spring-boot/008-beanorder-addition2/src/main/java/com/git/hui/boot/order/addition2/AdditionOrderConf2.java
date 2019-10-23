package com.git.hui.boot.order.addition2;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 16:35 19/10/17.
 */
@Configuration
@AutoConfigureOrder(-1)
@ComponentScan("com.git.hui.boot.order.addition2")
public class AdditionOrderConf2 {

    public AdditionOrderConf2() {
        System.out.println("additionOrderConf2 init!!!");
    }
}
