package com.git.hui.extention.definition.register;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * @author YiHui
 * @date 2022/10/26
 */
public class DemoBean implements InitializingBean {
    private int initCode;

    public DemoBean() {
        initCode = new Random().nextInt(100);
        System.out.println("demo bean create! -> " + initCode);
    }

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct" + initCode);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet" + initCode);
    }

    public int getInitCode() {
        return initCode;
    }
}
