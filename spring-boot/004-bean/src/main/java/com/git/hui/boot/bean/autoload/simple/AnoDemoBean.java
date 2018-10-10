package com.git.hui.boot.bean.autoload.simple;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 17:01 18/9/30.
 */
@Component
public class AnoDemoBean {
    private String type = "AnoDemoBean";

    public String getName(String name) {
        return name + " _" + type;
    }
}
