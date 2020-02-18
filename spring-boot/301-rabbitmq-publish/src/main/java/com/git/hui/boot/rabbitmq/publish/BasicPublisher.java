package com.git.hui.boot.rabbitmq.publish;

import lombok.Data;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by @author yihui in 19:45 20/2/16.
 */
@Service
public class BasicPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String publish(String name) {
        return publish2mq1(name) + publish2mq2(name) + publish2mq3(name) + publish2mq4(name);
    }

    /**
     * 一般的用法，推送消息
     *
     * @param ans
     * @return
     */
    private String publish2mq1(String ans) {
        String msg = "Durable msg = " + ans;
        System.out.println("publish: " + msg);
        rabbitTemplate.convertAndSend(MqConstants.exchange, MqConstants.routing, msg);
        return msg;
    }


    /**
     * 推送一个非持久化的消息，这个消息推送到持久化的队列时，mq重启，这个消息会丢失；上面的持久化消息不会丢失
     *
     * @param ans
     * @return
     */
    private String publish2mq2(String ans) {
        MessageProperties properties = new MessageProperties();
        properties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        Message message = rabbitTemplate.getMessageConverter().toMessage("NonDurable = " + ans, properties);

        rabbitTemplate.convertAndSend(MqConstants.exchange, MqConstants.routing, message);

        System.out.println("publish: " + message);
        return message.toString();
    }


    private String publish2mq3(String ans) {
        String msg = "Define msg = " + ans;
        rabbitTemplate.convertAndSend(MqConstants.exchange, MqConstants.routing, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("ta", "测试");
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                return message;
            }
        });

        return msg;
    }


    private String publish2mq4(String ans) {
        try {
            NonSerDO nonSerDO = new NonSerDO(18, ans);
            System.out.println("publish: " + nonSerDO);
            rabbitTemplate.convertAndSend(MqConstants.exchange, MqConstants.routing, nonSerDO);
            return nonSerDO.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Data
    public static class NonSerDO {
        private Integer age;
        private String name;

        public NonSerDO(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }
}