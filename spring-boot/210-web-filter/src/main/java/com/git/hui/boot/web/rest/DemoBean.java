package com.git.hui.boot.web.rest;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:55 19/10/12.
 */
@Data
@Component
public class DemoBean {

    private long time;

    public DemoBean() {
        time = System.currentTimeMillis();
    }

    public void show() {
        System.out.println("demo bean!!! " + time);
    }

}
