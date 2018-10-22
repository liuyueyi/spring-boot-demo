package com.git.hui.boot.beanorder.choose.samename;

import com.git.hui.boot.beanorder.choose.samename.a.SameA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by @author yihui in 21:32 18/10/22.
 */
@Component
public class SameDemo {

    @Autowired
    private SameA sameA;

    @PostConstruct
    public void init() {
        sameA.print();
    }
}
