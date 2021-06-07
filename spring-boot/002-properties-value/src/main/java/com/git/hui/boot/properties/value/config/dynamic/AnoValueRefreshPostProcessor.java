package com.git.hui.boot.properties.value.config.dynamic;

import com.alibaba.fastjson.JSONObject;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置的动态刷新
 *
 * @author yihui
 * @date 2021/6/7
 */
@Component
public class AnoValueRefreshPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements EnvironmentAware {
    private Map<String, FieldPair> mapper = new HashMap<>();
    private Environment environment;

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        processMetaValue(bean);
        return super.postProcessAfterInstantiation(bean, beanName);
    }

    /**
     * 扫描bean的所有属性，并获取@MetaVal修饰的属性
     *
     * @param bean
     */
    private void processMetaValue(Object bean) {
        Class clz = bean.getClass();
        if (!clz.isAnnotationPresent(RefreshValue.class)) {
            return;
        }

        try {
            for (Field field : clz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Value.class)) {
                    Value val = field.getAnnotation(Value.class);
                    Pair<String, String> pair = pickPropertyKey(val.value());
                    mapper.put(pair.getKey(), new FieldPair(bean, field, val, pair.getValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * 实现一个基础的配置文件参数动态刷新支持
     *
     * @param value
     * @return
     */
    private Pair<String, String> pickPropertyKey(String value) {
        int start = value.indexOf("$") + 2;
        int middle = value.indexOf(":", start);
        int end = value.indexOf("}", start);

        String key;
        String defaultValue;
        if (middle > 0 && middle < end) {
            // 包含默认值
            key = value.substring(start, middle);
            defaultValue = value.substring(middle + 1, end);
        } else {
            // 不包含默认值
            key = value.substring(start, end);
            defaultValue = null;
        }
        return new Pair<>(key, defaultValue);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldPair {
        private static PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}");

        Object bean;
        Field field;
        Value value;
        String defaultVal;

        public void updateValue(Environment environment) throws IllegalAccessException {
            boolean access = field.isAccessible();
            if (!access) {
                field.setAccessible(true);
            }

            String val = value.value();
            if (defaultVal != null) {
                val = value.value().replace(":" + defaultVal, "");
            }

            String updateVal = propertyPlaceholderHelper.replacePlaceholders(val, environment::getProperty);
            field.set(bean, JSONObject.parseObject(updateVal, field.getType()));
            field.setAccessible(access);
        }
    }

    public static class ConfigUpdateEvent extends ApplicationEvent {
        String key;

        /**
         * Create a new {@code ApplicationEvent}.
         *
         * @param source the object on which the event initially occurred or with
         *               which the event is associated (never {@code null})
         */
        public ConfigUpdateEvent(Object source, String key) {
            super(source);
            this.key = key;
        }
    }

    @EventListener
    public void updateConfig(ConfigUpdateEvent configUpdateEvent) throws IllegalAccessException {
        FieldPair pair = mapper.get(configUpdateEvent.key);
        if (pair != null) {
            pair.updateValue(environment);
        }
    }
}
