package com.git.hui.boot.conditionbean.example.properties;

/**
 * Created by @author yihui in 10:17 18/10/18.
 */
public class PropertyValueExistBean {
    public String name;

    public PropertyValueExistBean(String name) {
        this.name = name;
    }

    public String getName() {
        return "property value exist: " + name;
    }
}
