package com.git.hui.boot.listener.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 2021/4/21
 */
@Component
public class StopListener implements ApplicationListener<ContextStoppedEvent> {
    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        System.out.println("stop 事件 ---" + event);
    }
}
