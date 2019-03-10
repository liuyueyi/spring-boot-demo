package com.git.hui.boot.aop.demo2;

import com.git.hui.boot.aop.annotation.AnoDot;
import com.git.hui.boot.aop.demo.DemoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by @author yihui in 15:49 19/3/2.
 */
@Component
public class AnoDemoBean {
    @Autowired
    private DemoBean demoBean;

    @Autowired
    private ScopeDemoBean scopeDemoBean;

    @AnoDot
    public String randUUID(long time) {
        try {
            System.out.println("in AnoDemoBean randUUID start!");
            return genUUID(time) + "<<<>>>" + demoBean.genUUID(time);
        } finally {
            System.out.println("in AnoDemoBean randUUID finally!");
        }
    }

    @AnoDot
    public String genUUID(long time) {
        try {
            System.out.println("in AnoDemoBean genUUID before process!");
            return UUID.randomUUID() + "|" + time;
        } finally {
            System.out.println("in AnoDemoBean genUUID finally!");
        }
    }


    public void scopeUUID(long time) {
        try {
            System.out.println("-------- default --------");
            String defaultAns = scopeDemoBean.defaultRandUUID(time);
            System.out.println("-------- default: " + defaultAns + " --------\n");


            System.out.println("-------- protected --------");
            String protectedAns = scopeDemoBean.protectedRandUUID(time);
            System.out.println("-------- protected: " + protectedAns + " --------\n");


            System.out.println("-------- private --------");
            Method method = ScopeDemoBean.class.getDeclaredMethod("privateRandUUID", long.class);
            method.setAccessible(true);
            String privateAns = (String) method.invoke(scopeDemoBean, time);
            System.out.println("-------- private: " + privateAns + " --------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
