package com.git.hui.boot.dynamicbean.manual;

import com.git.hui.boot.dynamicbean.bean.OriginBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * 手动注册的Bean，测试如何使用其他的bean的case
 *
 * Created by @author yihui in 11:56 18/10/13.
 */
@Slf4j
public class ManualDIBean {

    private int id;

    @Autowired
    private OriginBean originBean;

    private String name;

    public ManualDIBean(String name) {
        Random random = new Random();
        this.id = random.nextInt(100);
        this.name = name;
    }

    public String print(String msg) {
        String o = originBean.print(" call by ManualDIBean! ");
        return "[ManualDIBean] print: " + msg + " id: " + id + " name: " + name + " originBean print:" + o;
    }
}
