package com.git.hui.boot.order.addition2.bean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * {@link @Order}注解无法控制初始化顺序，这里是错误示例
 * Created by @author yihui in 16:28 19/10/17.
 */
@Order(3)
@Component
public class BDemo {
    private String name = "B demo";

    public BDemo() {
//        System.out.println(name);
    }
}
