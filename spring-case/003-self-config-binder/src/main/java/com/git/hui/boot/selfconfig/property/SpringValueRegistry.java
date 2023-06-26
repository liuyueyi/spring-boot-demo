package com.git.hui.boot.selfconfig.property;

import com.git.hui.boot.selfconfig.util.SpringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * 配置变更注册
 *
 * @author YiHui
 * @date 2023/6/26
 */
@Slf4j
public class SpringValueRegistry {
    @Data
    public static class SpringValue {
        private MethodParameter methodParameter;
        private Field field;
        private WeakReference<Object> beanRef;
        private String beanName;
        private String key;
        private String placeholder;
        private Class<?> targetType;

        public SpringValue(String key, String placeholder, Object bean, String beanName, Field field) {
            this.beanRef = new WeakReference<>(bean);
            this.beanName = beanName;
            this.field = field;
            this.key = key;
            this.placeholder = placeholder;
            this.targetType = field.getType();
        }

        public SpringValue(String key, String placeholder, Object bean, String beanName, Method method) {
            this.beanRef = new WeakReference<>(bean);
            this.beanName = beanName;
            this.methodParameter = new MethodParameter(method, 0);
            this.key = key;
            this.placeholder = placeholder;
            Class<?>[] paramTps = method.getParameterTypes();
            this.targetType = paramTps[0];
        }

        public void update(BiFunction<String, Class, Object> newVal) throws Exception {
            if (isField()) {
                injectField(newVal);
            } else {
                injectMethod(newVal);
            }
        }

        private void injectField(BiFunction<String, Class, Object> newVal) throws Exception {
            Object bean = beanRef.get();
            if (bean == null) {
                return;
            }
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(bean, newVal.apply(key, field.getType()));
            field.setAccessible(accessible);
            log.info("更新value: {}#{} = {}", beanName, field.getName(), field.get(bean));
        }

        private void injectMethod(BiFunction<String, Class, Object> newVal)
                throws Exception {
            Object bean = beanRef.get();
            if (bean == null) {
                return;
            }
            Object va = newVal.apply(key, methodParameter.getParameterType());
            methodParameter.getMethod().invoke(bean, va);
            log.info("更新method: {}#{} = {}", beanName, methodParameter.getMethod().getName(), va);
        }

        public boolean isField() {
            return this.field != null;
        }
    }


    public static Map<String, Set<SpringValue>> registry = new ConcurrentHashMap<>();

    public static void register(String key, SpringValue val) {
        if (!registry.containsKey(key)) {
            synchronized (SpringValueRegistry.class) {
                if (!registry.containsKey(key)) {
                    registry.put(key, new HashSet<>());
                }
            }
        }

        Set<SpringValue> set = registry.get(key);
        set.add(val);
    }

    public static void updateValue(String key) {
        Set<SpringValue> set = registry.get(key);
        if (set != null) {
            set.forEach(s -> {
                try {
                    s.update((s1, aClass) -> SpringUtil.getBinder().bindOrCreate(s1, aClass));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
