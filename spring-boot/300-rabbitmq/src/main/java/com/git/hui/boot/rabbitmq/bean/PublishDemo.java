package com.git.hui.boot.rabbitmq.bean;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 12:20 20/2/10.
 */
@Component
public class PublishDemo {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public String publish2mq(String ans) {
        String msg = "hello world = " + ans;
        System.out.println("publish: " + msg);
        amqpTemplate.convertAndSend(Pkg.exchange, Pkg.routing, msg);
        return msg;
    }
}
