package com.git.hui.boot.rabbitmq.rest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 15:14 20/3/18.
 */
@RestController
public class PublishRest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping(path = "publish")
    public boolean publish(String exchange, String routing, String data) {
        rabbitTemplate.convertAndSend(exchange, routing, data);
        return true;
    }

}
