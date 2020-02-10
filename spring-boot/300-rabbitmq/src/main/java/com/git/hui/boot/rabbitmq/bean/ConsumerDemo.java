package com.git.hui.boot.rabbitmq.bean;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * Created by @author yihui in 12:28 20/2/10.
 */
@Service
public class ConsumerDemo {

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = Pkg.queue, durable = "false", autoDelete = "true"),
            exchange = @Exchange(value = Pkg.exchange, ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC), key = Pkg.routing))
    public void consumer(String msg) {
        System.out.println("consumer msg: " + msg);
    }
}
