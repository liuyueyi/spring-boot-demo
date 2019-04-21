package com.git.hui.boot.websocket.basic;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * websocket handler 返回数据
 * Created by @author yihui in 10:55 19/4/16.
 */
public class MyWebSocketHandler extends TextWebSocketHandler {
    private static Set<WebSocketSession> tmpCache = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        tmpCache.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        tmpCache.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获取接收到的数据
        String payload = message.getPayload();

        // 向客户端发送数据
        session.sendMessage(new TextMessage("response: " + payload));
    }

    public static void groupSend() throws IOException {
        for (WebSocketSession session : tmpCache) {
            session.sendMessage(new TextMessage("自动返回: " + System.currentTimeMillis()));
        }
    }
}
