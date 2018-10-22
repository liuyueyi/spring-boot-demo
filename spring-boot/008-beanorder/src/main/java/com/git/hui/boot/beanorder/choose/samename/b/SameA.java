package com.git.hui.boot.beanorder.choose.samename.b;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 21:33 18/10/22.
 */
@Component
public class SameA {
    private String text;

    public SameA() {
        text = "B SameA";
    }

    public void print() {
        System.out.println(text);
    }
}
