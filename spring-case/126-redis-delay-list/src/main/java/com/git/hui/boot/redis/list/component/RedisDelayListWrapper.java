package com.git.hui.boot.redis.list.component;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yihui
 * @date 2021/5/7
 */
@Component
public class RedisDelayListWrapper implements ApplicationContextAware {
    private static final Long DELETE_SUCCESS = 1L;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private Set<String> topic = new CopyOnWriteArraySet<>();

    public void publish(String key, Object val, long delayTime) {
        topic.add(key);
        String strVal = val instanceof String ? (String) val : JSONObject.toJSONString(val);

        redisTemplate.opsForZSet().add(key, strVal, System.currentTimeMillis() + delayTime);
    }

    public String fetchOne(String key) {
        Set<String> sets = redisTemplate.opsForZSet().rangeByScore(key, 0, System.currentTimeMillis(), 0, 3);
        if (CollectionUtils.isEmpty(sets)) {
            return null;
        }

        for (String val : sets) {
            if (DELETE_SUCCESS.equals(redisTemplate.opsForZSet().remove(key, val))) {
                // 删除成功，表示抢占到
                return val;
            }
        }
        return null;
    }

    @Scheduled(fixedRate = 10_000)
    public void schedule() {
        for (String specialTopic : topic) {
            String cell = fetchOne(specialTopic);
            if (cell != null) {
                applicationContext.publishEvent(new DelayMsg(this, cell, specialTopic));
            }
        }
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ToString
    public static class DelayMsg extends ApplicationEvent {
        @Getter
        private String msg;
        @Getter
        private String topic;

        /**
         * Create a new {@code ApplicationEvent}.
         *
         * @param source the object on which the event initially occurred or with
         *               which the event is associated (never {@code null})
         */
        public DelayMsg(Object source, String msg, String topic) {
            super(source);
            this.msg = msg;
            this.topic = topic;
        }
    }
}
