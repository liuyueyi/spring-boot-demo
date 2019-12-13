package com.git.hui.boot.importbean.bean;

import com.git.hui.boot.importbean.anno.Meta;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by @author yihui in 12:08 19/12/13.
 */
@Meta
public class DependBean2 {
    @Autowired
    public DependBean2(DemoBean1 demoBean1) {
        System.out.println("depend bean2! " + demoBean1);
    }
}
