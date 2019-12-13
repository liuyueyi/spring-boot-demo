package com.git.hui.boot.importbean.bean;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 12:09 19/12/13.
 */
@Component
public class ABean {

    public ABean(DemoBean1 demoBean1) {
        System.out.println("a bean : " + demoBean1);
    }
}
