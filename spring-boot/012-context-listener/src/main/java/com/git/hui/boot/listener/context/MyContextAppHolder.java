package com.git.hui.boot.listener.context;

import com.git.hui.boot.listener.demo.DemoBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.UUID;

/**
 * Created by @author yihui in 08:49 19/8/21.
 */
public class MyContextAppHolder implements FactoryBean<DemoBean>, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("context aware init");
        this.applicationContext = applicationContext;
    }

    @Override
    public DemoBean getObject() throws Exception {
        System.out.println("init bean!" + UUID.randomUUID().toString());
        MyContextListener listener = (MyContextListener) applicationContext.getBean("myContextListener");
        return new DemoBean(listener.getNum());
    }

    @Override
    public Class<?> getObjectType() {
        return DemoBean.class;
    }
}
