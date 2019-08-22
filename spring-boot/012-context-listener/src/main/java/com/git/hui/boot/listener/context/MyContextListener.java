package com.git.hui.boot.listener.context;

import lombok.Getter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by @author yihui in 08:48 19/8/21.
 */
public class MyContextListener implements ServletContextListener {
    @Getter
    private volatile int num = 0;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("-------> context loader!");
        num += 1;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("===== context destroyed! ======");
    }
}
