package com.git.hui.boot.dynamicbean.manual;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Created by @author yihui in 11:56 18/10/13.
 */
@Slf4j
public class ManualBean {

    private int id;

    public ManualBean() {
        Random random = new Random();
        id = random.nextInt(100);
    }

    public String print(String msg) {
        return "[ManualBean] print : " + msg + " id: " + id;
    }
}
