package com.git.hui.boot.listener.basic;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author yihui
 * @date 21/4/29
 */
@Service
public class MsgPublisher implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void publish(String msg) {
        applicationContext.publishEvent(new MsgEvent(this, msg));
    }


    @EventListener(MsgEvent.class)
    public void consumer(MsgEvent msgEvent) {
        System.out.println("receive msg by @anno: " + msgEvent);
    }

}
