package com.git.hui.boot.conditionbean.example.depends.bean;

/**
 * 当依赖的bean存在时才加载
 * Created by @author yihui in 09:16 18/10/18.
 */
public class LoadIfBeanExist {

    private String name;

    public LoadIfBeanExist(String name) {
        this.name = name;
    }

    public String getName() {
        return "load if bean exists: " + name;
    }
}
