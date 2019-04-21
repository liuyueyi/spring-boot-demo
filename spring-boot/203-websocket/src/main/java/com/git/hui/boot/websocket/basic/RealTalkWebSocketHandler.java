package com.git.hui.boot.websocket.basic;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by @author yihui in 16:29 19/4/21.
 */
public class RealTalkWebSocketHandler extends TextWebSocketHandler {

    private static Set<WebSocketSession> tmpCache = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        tmpCache.add(session);
        sendMsg("欢迎" + session.getId() + "进入聊天室");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        tmpCache.remove(session);
        sendMsg(session.getId() + " 离开聊天室");
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获取接收到的数据
        String payload = message.getPayload();
        sendMsg(session.getId() + " ： " + payload);
    }


    private void sendMsg(String msg) throws IOException {
        for (WebSocketSession session : tmpCache) {
            session.sendMessage(new TextMessage(msg + " | " + LocalDateTime.now()));
        }
    }
}
