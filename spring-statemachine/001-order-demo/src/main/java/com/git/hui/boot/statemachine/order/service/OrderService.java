package com.git.hui.boot.statemachine.order.service;

import com.git.hui.boot.statemachine.order.model.Order;
import com.git.hui.boot.statemachine.order.model.OrderStatus;
import com.git.hui.boot.statemachine.order.model.OrderStatusChangeEvent;
import lombok.Getter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yihui
 * @date 21/1/31
 */
@Service
@WithStateMachine(name = "orderStateMachine")
public class OrderService {
    @Resource(name = "orderStateMachine")
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    private volatile long orderId = 1;
    @Getter
    private Map<Long, Order> orders = new ConcurrentHashMap<>();

    public Order creat() {
        Order order = new Order();
        order.setStatus(OrderStatus.WAIT_PAYMENT);
        order.setOrderId(orderId++);
        orders.put(order.getOrderId(), order);
        return order;
    }

    public Order pay(long orderId) {
        Order order = orders.get(orderId);
        System.out.println("to pay: " + order);
        Message<OrderStatusChangeEvent> msg = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED)
                .setHeader("order", order).build();
        if (!send(msg)) {
            System.out.println("pay error for: " + order);
        }
        return orders.get(orderId);
    }

    public Order deliver(long orderId) {
        Order order = orders.get(orderId);
        System.out.println("to delivery: " + order);
        Message<OrderStatusChangeEvent> msg = MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY)
                .setHeader("order", order).build();
        if (!send(msg)) {
            System.out.println("delivery error for: " + order);
        }
        return orders.get(orderId);
    }

    public Order receiver(long orderId) {
        Order order = orders.get(orderId);
        System.out.println("to receive: " + order);
        Message<OrderStatusChangeEvent> msg = MessageBuilder.withPayload(OrderStatusChangeEvent.RECEIVED)
                .setHeader("order", order).build();
        if (!send(msg)) {
            System.out.println("receive error for: " + order);
        }
        return orders.get(orderId);
    }


    private synchronized boolean send(Message<OrderStatusChangeEvent> msg) {
        boolean ans = false;
        try {
            orderStateMachine.start();
            Thread.sleep(1000);
            System.out.println("-------- start to send: " + msg);
            ans = orderStateMachine.sendEvent(msg);
            System.out.println("-------- send msg over: " + msg + " : " + ans);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @OnTransition
    public @interface StatesOnTransition {

        OrderStatus[] source() default {};

        OrderStatus[] target() default {};

    }

    @StatesOnTransition(source = OrderStatus.WAIT_PAYMENT, target = OrderStatus.WAIT_DELIVER)
    public boolean payTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_DELIVER);
        System.out.println("支付 headers=" + message.getHeaders().toString());
        return true;
    }

    @StatesOnTransition(source = OrderStatus.WAIT_DELIVER, target = OrderStatus.WAIT_RECEIVE)
    public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_RECEIVE);
        System.out.println("发货 headers=" + message.getHeaders().toString());
        return true;
    }

    @StatesOnTransition(source = OrderStatus.WAIT_RECEIVE, target = OrderStatus.FINISH)
    public boolean receiveTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.FINISH);
        System.out.println("收货 headers=" + message.getHeaders().toString());
        return true;
    }

}
