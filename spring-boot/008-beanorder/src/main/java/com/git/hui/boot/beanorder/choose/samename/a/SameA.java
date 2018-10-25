package com.git.hui.boot.beanorder.choose.samename.a;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 21:32 18/10/22.
 */
@Component
public class SameA {
    private String text ;
    public SameA() {
        text = "a sameA!";
    }

    public void print() {
        System.out.println(text);
    }
}
