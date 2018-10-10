package com.git.hui.boot.bean.autoload.simple;

import lombok.Data;

/**
 * Created by @author yihui in 17:00 18/9/30.
 */
@Data
public class ConfigDemoBean {
    private String type = "ConfigDemoBean";

    public String getName(String name) {
        return name + " _" + type;
    }
}
