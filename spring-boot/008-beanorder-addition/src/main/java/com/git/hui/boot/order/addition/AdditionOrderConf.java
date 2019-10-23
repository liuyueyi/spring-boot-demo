package com.git.hui.boot.order.addition;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 16:27 19/10/17.
 */
@AutoConfigureBefore
@AutoConfigureOrder(1)
@Configuration
@ComponentScan(value = {"com.git.hui.boot.order.addition"})
public class AdditionOrderConf {

    public AdditionOrderConf() {
        System.out.println("additionOrderConf init!!!");
    }

}
