package com.git.hui.boot.listener.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 2021/4/21
 */
@Component
public class CloseListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("close 事件 ---" + event);
    }
}
