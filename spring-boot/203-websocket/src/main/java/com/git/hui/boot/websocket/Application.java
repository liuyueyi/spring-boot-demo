package com.git.hui.boot.websocket;

import com.git.hui.boot.websocket.basic.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by @author yihui in 10:55 19/4/16.
 */
@EnableScheduling
@SpringBootApplication
public class Application {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

//    @Scheduled(cron = "0/10 * * * * ?")
//    public void sc1() throws IOException {
//        String rspMsg = Thread.currentThread().getName() + " 自动返回 | sc1：" + LocalDateTime.now();
//        MyWebSocketHandler.groupSend();
//
//        simpMessagingTemplate.convertAndSend("/topic/hello", rspMsg);
//    }

}
