package com.git.hui.boot.statemachine.order.config;

import com.git.hui.boot.statemachine.order.model.OrderStatus;
import com.git.hui.boot.statemachine.order.model.OrderStatusChangeEvent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableWithStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * @author yihui
 * @date 21/1/31
 */
@Configuration
@EnableWithStateMachine
public class OrderStatemachineConfigure extends EnumStateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {
//    @Override
//    public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderStatusChangeEvent> config)
//            throws Exception {
//        config
//                .withConfiguration()
//                .autoStartup(true)
//                .listener(listener());
//    }
//
//    @Override
//    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states)
//            throws Exception {
//        states
//                .withStates()
//                .initial(OrderStatus.WAIT_PAYMENT)
//                .states(EnumSet.allOf(OrderStatus.class));
//    }
//
//    @Override
//    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions)
//            throws Exception {
//        transitions
//                .withExternal()
//                .source(OrderStatus.WAIT_PAYMENT)
//                .target(OrderStatus.WAIT_DELIVER)
//                .event(OrderStatusChangeEvent.PAYED)
//                .and()
//                .withExternal().
//                source(OrderStatus.WAIT_DELIVER)
//                .target(OrderStatus.WAIT_RECEIVE)
//                .event(OrderStatusChangeEvent.DELIVERY)
//                .and()
//                .withExternal()
//                .source(OrderStatus.WAIT_RECEIVE)
//                .target(OrderStatus.FINISH)
//                .event(OrderStatusChangeEvent.RECEIVED);
//    }
//
//    @Bean
//    public StateMachineListener<OrderStatus, OrderStatusChangeEvent> listener() {
//        return new StateMachineListenerAdapter<OrderStatus, OrderStatusChangeEvent>() {
//
//            @Override
//            public void stateChanged(org.springframework.statemachine.state.State<OrderStatus, OrderStatusChangeEvent> from,
//                                     org.springframework.statemachine.state.State<OrderStatus, OrderStatusChangeEvent> to) {
//                if (from != null) {
//                    System.out.println("State change from: "+ from.getId() +  " to: " + to.getId());
//                } else {
//                    System.out.println("State change to: " + to.getId());
//                }
//            }
//
//            @Override
//            public void transition(Transition<OrderStatus, OrderStatusChangeEvent> transition) {
//                if (transition.getTarget().getId() == OrderStatus.WAIT_PAYMENT) {
//                    System.out.println("订单创建，待支付");
//                }
//            }
//        };
//    }


    @Bean(name = "orderStateMachine")
    public StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<OrderStatus, OrderStatusChangeEvent> builder = StateMachineBuilder.builder();

        System.out.println("构建订单状态机");

        builder.configureConfiguration()
                .withConfiguration()
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));

        builder.configureTransitions()
                .withExternal()
                .source(OrderStatus.WAIT_PAYMENT)
                .target(OrderStatus.WAIT_DELIVER)
                .event(OrderStatusChangeEvent.PAYED)
                .and()
                .withExternal().
                source(OrderStatus.WAIT_DELIVER)
                .target(OrderStatus.WAIT_RECEIVE)
                .event(OrderStatusChangeEvent.DELIVERY)
                .and()
                .withExternal()
                .source(OrderStatus.WAIT_RECEIVE)
                .target(OrderStatus.FINISH)
                .event(OrderStatusChangeEvent.RECEIVED);

        return builder.build();
    }
}
