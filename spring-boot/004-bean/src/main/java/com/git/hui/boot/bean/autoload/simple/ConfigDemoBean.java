package com.git.hui.boot.bean.autoload.simple;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author yihui in 17:00 18/9/30.
 */
@Slf4j
@Data
public class ConfigDemoBean {
    private String type = "ConfigDemoBean";

    public ConfigDemoBean() {
        log.info("ConfigDemoBean load time: {}", System.currentTimeMillis());
    }

    public String getName(String name) {
        return name + " _" + type;
    }
}
