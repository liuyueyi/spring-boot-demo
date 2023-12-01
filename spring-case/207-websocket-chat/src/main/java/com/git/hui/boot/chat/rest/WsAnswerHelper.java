package com.git.hui.boot.chat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author YiHui
 * @date 2023/11/24
 */
@Component
public class WsAnswerHelper {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private static WsAnswerHelper instance;

    @PostConstruct
    public void init() {
        WsAnswerHelper.instance = this;
    }

    public static void publish(String destination, Object msg) {
        instance.simpMessagingTemplate.convertAndSend(destination, msg);
    }

    public static void publish(String user, String destination, Object msg) {
        instance.simpMessagingTemplate.convertAndSendToUser(user, destination, msg);
    }
}
