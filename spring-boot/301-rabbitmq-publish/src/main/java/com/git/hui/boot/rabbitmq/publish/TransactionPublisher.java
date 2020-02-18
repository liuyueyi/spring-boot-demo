package com.git.hui.boot.rabbitmq.publish;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * 事务机制
 * Created by @author yihui in 19:43 20/2/17.
 */
@Service
public class TransactionPublisher implements RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate transactionRabbitTemplate;

    @PostConstruct
    public void init() {
        // 将信道设置为事务模式
        transactionRabbitTemplate.setChannelTransacted(true);
        transactionRabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("事务 " + message + " 发送失败");
    }

    /**
     * 一般的用法，推送消息
     *
     * @param ans
     * @return
     */
    @Transactional(rollbackFor = Exception.class, transactionManager = "rabbitTransactionManager")
    public String publish(String ans) {
        String msg = "transaction msg = " + ans;
        System.out.println("publish: " + msg);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        transactionRabbitTemplate.convertAndSend(MqConstants.exchange, MqConstants.routing, msg, correlationData);
        return msg;
    }
}
