package com.git.hui.boot.listener.basic;

import org.springframework.context.ApplicationEvent;

/**
 * @author yihui
 * @date 21/4/29
 */
public class MsgEvent extends ApplicationEvent {
    private String msg;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MsgEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MsgEvent{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
