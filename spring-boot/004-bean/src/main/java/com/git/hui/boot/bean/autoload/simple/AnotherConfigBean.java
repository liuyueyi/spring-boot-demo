package com.git.hui.boot.bean.autoload.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author yihui in 17:20 18/10/10.
 */
@Slf4j
public class AnotherConfigBean {
    private String type = "AnotherConfigBean";

    public AnotherConfigBean() {
        log.info("AnotherConfigBean load time: {}", System.currentTimeMillis());
    }

    public String getName(String name) {
        return name + " _" + type;
    }
}
