package com.git.hui.boot.conditionbean.example.depends.clz;

/**
 * Created by @author yihui in 09:26 18/10/18.
 */
public class LoadIfClzExists {
    private String name;

    public LoadIfClzExists(String name) {
        this.name = name;
    }

    public String getName() {
        return "load if exists clz: " + name;
    }
}
