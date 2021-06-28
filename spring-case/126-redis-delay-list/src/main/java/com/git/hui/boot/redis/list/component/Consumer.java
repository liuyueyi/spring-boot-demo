package com.git.hui.boot.redis.list.component;

import org.springframework.context.event.EventListener;

import java.lang.annotation.*;

/**
 * @author yihui
 * @date 2021/5/7
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EventListener
public @interface Consumer {
    String topic();
}
