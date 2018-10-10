package com.git.hui.boot.bean.autoload.factory;

/**
 * Created by @author yihui in 17:19 18/9/30.
 */
public class FacDemoBean {
    private String type = "FacDemoBean";

    public String getName(String name) {
        return name + " _" + type;
    }
}
