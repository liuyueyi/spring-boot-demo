package com.git.hui.boot.websocket.stomp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * Created by @author yihui in 10:37 19/4/18.
 */
@Controller
public class HelloController {

    /**
     * 当接受到客户端发送的消息时, 发送的路径是： /app/hello (这个/app前缀是 StompConfiguration 中的配置的)
     * 将返回结果推送给所有订阅了 /topic/hello 的消费者
     *
     * @param content
     * @return
     */
    @MessageMapping("/hello")
    @SendTo("/topic/hello")
    public String sayHello(String content) {
        return "resp: " + content + " | " + LocalDateTime.now();
    }
}
