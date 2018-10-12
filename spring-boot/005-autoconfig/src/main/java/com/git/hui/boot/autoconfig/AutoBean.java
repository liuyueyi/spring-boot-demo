package com.git.hui.boot.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:58 18/10/10.
 */
@Slf4j
@Component
public class AutoBean {
    private String name;

    public AutoBean() {
        this("defaultAutoBean");
    }

    public AutoBean(String name) {
        this.name = name;
        log.info("AutoBean load time: {}", System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }
}
