package com.git.hui.boot.statemachine.order;

import com.git.hui.boot.statemachine.order.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 21/1/31
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    public Application(OrderService orderService) {
        orderService.creat();

        orderService.pay(1L);
        orderService.deliver(1L);
        orderService.receiver(1L);

        System.out.println(orderService.getOrders());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
