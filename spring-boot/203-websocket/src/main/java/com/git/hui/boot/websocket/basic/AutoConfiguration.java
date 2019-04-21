package com.git.hui.boot.websocket.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.concurrent.Executors;

/**
 * Created by @author yihui in 11:23 19/4/16.
 */
@Configuration
@EnableWebSocket
public class AutoConfiguration implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "wsdemo");
        registry.addHandler(realTalkWebSocketHandler(), "talk");
    }

    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }

    public WebSocketHandler realTalkWebSocketHandler() {
        return new RealTalkWebSocketHandler();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());
    }
}
