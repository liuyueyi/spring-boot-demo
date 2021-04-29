package com.git.hui.boot.listener.basic;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author yihui
 * @date 21/4/29
 */
@Service
public class MsgEventListener implements ApplicationListener<MsgEvent> {
    @Override
    public void onApplicationEvent(MsgEvent event) {
        System.out.println("receive msg event: " + event);
    }
}
