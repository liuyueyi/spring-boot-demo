package com.git.hui.boot.conditionbean.example.depends.bean;

/**
 * Created by @author yihui in 09:20 18/10/18.
 */
public class LoadIfBeanNotExists {
    public String name;

    public LoadIfBeanNotExists(String name) {
        this.name = name;
    }

    public String getName() {
        return "load if bean not exists: " + name;
    }
}
