package com.git.hui.boot.listener.demo;

import lombok.Getter;

/**
 * Created by @author yihui in 08:52 19/8/21.
 */
public class DemoBean {
    @Getter
    private int num;

    public DemoBean(int num) {
        this.num = num;
    }

}
