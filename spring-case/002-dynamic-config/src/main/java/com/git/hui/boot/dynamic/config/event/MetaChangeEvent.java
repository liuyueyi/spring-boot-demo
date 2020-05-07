package com.git.hui.boot.dynamic.config.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * Created by @author yihui in 21:47 20/4/21.
 */
@ToString
@EqualsAndHashCode
public class MetaChangeEvent extends ApplicationEvent {
    private static final long serialVersionUID = -9100039605582210577L;
    private String key;

    private String oldVal;

    private String newVal;


    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MetaChangeEvent(Object source) {
        super(source);
    }

    public MetaChangeEvent(Object source, String key, String oldVal, String newVal) {
        super(source);
        this.key = key;
        this.oldVal = oldVal;
        this.newVal = newVal;
    }

    public String getKey() {
        return key;
    }

    public String getOldVal() {
        return oldVal;
    }

    public String getNewVal() {
        return newVal;
    }
}
