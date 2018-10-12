package com.git.hui.boot.bean.autoload.factory;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author yihui in 17:19 18/9/30.
 */
@Slf4j
public class FacDemoBean {
    private String type = "FacDemoBean";

    public FacDemoBean() {
        log.info("FacDemoBean load time: {}", System.currentTimeMillis());
    }

    public String getName(String name) {
        return name + " _" + type;
    }
}
