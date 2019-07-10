package com.git.hui.boot.aop.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by @author yihui in 09:46 19/7/10.
 */
@Component
public class ProxyFactoryDemoService {
    @Autowired
    private DemoInter demoInter;

    public void testShow() {
        demoInter.showName("hello");
    }

}
