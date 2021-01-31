package com.git.hui.boot.statemachine.order.model;

/**
 * @author yihui
 * @date 21/1/31
 */
public enum OrderStatus {
    // 待支付，待发货，待收货，订单结束
    WAIT_PAYMENT, WAIT_DELIVER, WAIT_RECEIVE, FINISH;
}
