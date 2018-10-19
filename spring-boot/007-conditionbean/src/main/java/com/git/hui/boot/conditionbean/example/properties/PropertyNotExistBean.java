package com.git.hui.boot.conditionbean.example.properties;

/**
 * Created by @author yihui in 09:51 18/10/18.
 */
public class PropertyNotExistBean {
    private String name;

    public PropertyNotExistBean(String name) {
        this.name = name;
    }

    public String getName() {
        return "no property" + name;
    }
}
