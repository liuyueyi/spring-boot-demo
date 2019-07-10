package com.git.hui.boot.aop.factory;

import org.springframework.stereotype.Service;

/**
 * Created by @author yihui in 10:16 19/7/10.
 */
//@Service("demoService")
public class DemoService implements DemoInter {
    @Override
    public void showName(String name) {
        System.out.println(name);
    }

}
