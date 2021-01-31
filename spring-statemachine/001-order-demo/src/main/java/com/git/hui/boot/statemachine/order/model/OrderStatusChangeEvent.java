package com.git.hui.boot.statemachine.order.model;

/**
 * @author yihui
 * @date 21/1/31
 */
public enum OrderStatusChangeEvent {
    // 支付，发货，确认收货
    PAYED, DELIVERY, RECEIVED;
}
