package com.git.hui.boot.listener.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 2021/4/21
 */
@Component
public class StartListener implements ApplicationListener<ContextStartedEvent> {
    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("start 事件 ---" + event);
    }
}
