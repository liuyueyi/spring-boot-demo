package com.git.hui.boot.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * 为每个连接，确定对应的用户身份，用于后端像用户发送私人信息时使用
 *
 * @author YiHui
 * @date 2023/6/8
 */
@Slf4j
public class AuthHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String uname = (String) attributes.get("uname");
        // 这里的用户身份标识字段，再使用下面的这个方法响应客户端消息时，与第一个参数user传值进行匹配；当两者相同时，表示这个消息就是给这个用户的
        // org.springframework.messaging.simp.SimpMessagingTemplate.convertAndSendToUser(java.lang.String, java.lang.String, java.lang.Object)
        return () -> uname;
    }
}
