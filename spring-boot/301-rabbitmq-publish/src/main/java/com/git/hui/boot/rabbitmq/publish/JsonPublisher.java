package com.git.hui.boot.rabbitmq.publish;

import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 21:19 20/2/16.
 */
@Service
public class JsonPublisher {
    @Autowired
    private RabbitTemplate jsonRabbitTemplate;

    @Autowired
    private RabbitTemplate jacksonRabbitTemplate;

    /**
     * 一般的用法，推送消息
     *
     * @param ans
     * @return
     */
    public String publish(String ans) {
        return publish1(ans) + publish2(ans) + publish3(ans);
    }

    private String publish1(String ans) {
        Map<String, Object> msg = new HashMap<>(8);
        msg.put("msg", ans);
        msg.put("type", "json");
        msg.put("version", 123);
        System.out.println("publish: " + msg);
        jsonRabbitTemplate.convertAndSend(MqConstants.exchange, MqConstants.routing, msg);
        return msg.toString();
    }

    private String publish2(String ans) {
        BasicPublisher.NonSerDO nonSerDO = new BasicPublisher.NonSerDO(18, "SELF_JSON" + ans);
        System.out.println("publish: " + nonSerDO);
        jsonRabbitTemplate.convertAndSend(MqConstants.exchange, MqConstants.routing, nonSerDO);
        return nonSerDO.toString();
    }

    private String publish3(String ans) {
        Map<String, Object> msg = new HashMap<>(8);
        msg.put("msg", ans);
        msg.put("type", "jackson");
        msg.put("version", 456);
        System.out.println("publish: " + msg);
        jacksonRabbitTemplate.convertAndSend(MqConstants.exchange, MqConstants.routing, msg);
        return msg.toString();
    }


}
