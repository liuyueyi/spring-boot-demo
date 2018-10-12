package com.git.hui.boot.bean.autoload.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 17:01 18/9/30.
 */
@Slf4j
@Component
public class AnoDemoBean {
    private String type = "AnoDemoBean";

    public AnoDemoBean() {
        log.info("AnoDemoBean load time: {}", System.currentTimeMillis());
    }

    public String getName(String name) {
        return name + " _" + type;
    }
}
