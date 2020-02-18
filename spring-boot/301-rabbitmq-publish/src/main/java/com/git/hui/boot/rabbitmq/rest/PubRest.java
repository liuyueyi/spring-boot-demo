package com.git.hui.boot.rabbitmq.rest;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.boot.rabbitmq.publish.AckPublisher;
import com.git.hui.boot.rabbitmq.publish.BasicPublisher;
import com.git.hui.boot.rabbitmq.publish.JsonPublisher;
import com.git.hui.boot.rabbitmq.publish.TransactionPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by @author yihui in 12:36 20/2/10.
 */
@RestController
public class PubRest {
    @Autowired
    private BasicPublisher basicPublisher;
    @Autowired
    private JsonPublisher jsonPublisher;
    @Autowired
    private AckPublisher ackPublisher;
    @Autowired
    private TransactionPublisher transactionPublisher;

    @GetMapping(path = {"", "/", "/publish"})
    public String publish(String name) {
        JSONObject obj = new JSONObject();
        obj.put("basic", basicPublisher.publish(name));
        obj.put("json", jsonPublisher.publish(name));
        obj.put("confirm", ackPublisher.publish(name));
        return obj.toJSONString();
    }

    @GetMapping(path = "json")
    public String json(String name) {
        return jsonPublisher.publish(name);
    }

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @GetMapping(path = "ack")
    public boolean ack(String name) {
        long start = System.currentTimeMillis();
        ackPublisher.publish(name + atomicInteger.getAndIncrement());
        ackPublisher.publish(name + atomicInteger.getAndIncrement());
        ackPublisher.publish(name + atomicInteger.getAndIncrement());
        System.out.println("ack cost: " + (System.currentTimeMillis() - start));
        return true;
    }

    @GetMapping(path = "transaction")
    public boolean transaction(String name) {
        long start = System.currentTimeMillis();
        transactionPublisher.publish(name + atomicInteger.getAndIncrement());
        transactionPublisher.publish(name + atomicInteger.getAndIncrement());
        transactionPublisher.publish(name + atomicInteger.getAndIncrement());
        System.out.println("transaction cost: " + (System.currentTimeMillis() - start));
        return true;
    }


    @GetMapping(path = "judge")
    public boolean judge(String name) {
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            ackPublisher.publish(name + atomicInteger.getAndIncrement());
            ackPublisher.publish(name + atomicInteger.getAndIncrement());
            ackPublisher.publish(name + atomicInteger.getAndIncrement());
            long mid = System.currentTimeMillis();
            System.out.println("ack cost: " + (mid - start));

            transactionPublisher.publish(name + atomicInteger.getAndIncrement());
            transactionPublisher.publish(name + atomicInteger.getAndIncrement());
            transactionPublisher.publish(name + atomicInteger.getAndIncrement());
            System.out.println("transaction cost: " + (System.currentTimeMillis() - mid));
        }
        return true;
    }
}
