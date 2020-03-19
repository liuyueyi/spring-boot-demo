package com.git.hui.boot.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 基于注解的消费方式
 * Created by @author yihui in 15:03 20/3/18.
 */
@Component
public class ListenerConsumer {

    /**
     * 当队列已经存在时，直接指定队列名的方式消费
     *
     * @param data
     */
    @RabbitListener(queues = "topic.a")
    public void consumerExistsQueue(String data) {
        System.out.println("consumerExistsQueue: " + data);
    }

    /**
     * 队列不存在时，需要创建一个队列，并且与exchange绑定
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "topic.n1", durable = "false", autoDelete = "true"),
            exchange = @Exchange(value = "topic.e", type = ExchangeTypes.TOPIC), key = "r"))
    public void consumerNoQueue(String data) {
        System.out.println("consumerNoQueue: " + data);
    }


    /**
     * 需要手动ack，但是不ack时
     *
     * @param data
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "topic.n2", durable = "false", autoDelete = "true"),
            exchange = @Exchange(value = "topic.e", type = ExchangeTypes.TOPIC), key = "r"), ackMode = "MANUAL")
    public void consumerNoAck(String data) {
        // 要求手动ack，这里不ack，会怎样?
        System.out.println("consumerNoAck: " + data);
    }


    /**
     * 手动ack
     *
     * @param data
     * @param deliveryTag
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "topic.n3", durable = "false", autoDelete = "true"),
            exchange = @Exchange(value = "topic.e", type = ExchangeTypes.TOPIC), key = "r"), ackMode = "MANUAL")
    public void consumerDoAck(String data, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel)
            throws IOException {
        System.out.println("consumerDoAck: " + data);

        if (data.contains("success")) {
            // RabbitMQ的ack机制中，第二个参数返回true，表示需要将这条消息投递给其他的消费者重新消费
            channel.basicAck(deliveryTag, false);
        } else {
            // 第三个参数true，表示这个消息会重新进入队列
            channel.basicNack(deliveryTag, false, true);
        }
    }


    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "topic.n4", durable = "false", autoDelete = "true"),
            exchange = @Exchange(value = "topic.e", type = ExchangeTypes.TOPIC), key = "r"), concurrency = "4")
    public void multiConsumer(String data) {
        System.out.println("multiConsumer: " + data);
    }
}
