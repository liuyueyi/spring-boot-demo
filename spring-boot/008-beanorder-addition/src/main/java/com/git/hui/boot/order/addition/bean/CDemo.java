package com.git.hui.boot.order.addition.bean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * {@link @Order}注解无法控制初始化顺序，这里是错误示例
 * Created by @author yihui in 16:28 19/10/17.
 */
@Order(2)
@Component
public class CDemo {
    private String name = "C demo";

    public CDemo() {
//        System.out.println(name);
    }
}
