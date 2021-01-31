package com.git.hui.boot.statemachine.order.model;

import lombok.Data;

/**
 * @author yihui
 * @date 21/1/31
 */
@Data
public class Order {
    private Long orderId;
    private OrderStatus status;
}
