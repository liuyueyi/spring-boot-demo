package com.git.hui.boot.dynamic.config.container;

import com.git.hui.boot.dynamic.config.anno.MetaVal;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by @author yihui in 21:26 20/4/21.
 */
@Slf4j
public class MetaContainer {
    private MetaValHolder metaValHolder;

    private Map<String, Set<InvokeCell>> metaCache = new ConcurrentHashMap<>();

    public MetaContainer(MetaValHolder metaValHolder) {
        this.metaValHolder = metaValHolder;
    }

    public String getProperty(String key) {
        return metaValHolder.getProperty(key);
    }

    public void addInvokeCell(MetaVal metaVal, Object target, Field field) throws IllegalAccessException {
        String metaKey = metaVal.value();
        if (!metaCache.containsKey(metaKey)) {
            synchronized (this) {
                if (!metaCache.containsKey(metaKey)) {
                    metaCache.put(metaKey, new HashSet<>());
                }
            }
        }

        metaCache.get(metaKey).add(new InvokeCell(metaVal, target, field, getProperty(metaKey)));
    }

    public void updateMetaVal(String metaKey, String oldVal, String newVal) {
        Set<InvokeCell> cacheSet = metaCache.get(metaKey);
        if (CollectionUtils.isEmpty(cacheSet)) {
            return;
        }

        cacheSet.forEach(s -> {
            try {
                s.update(newVal);
                log.info("update {} from {} to {}", s.getSignature(), oldVal, newVal);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Data
    public static class InvokeCell {
        private MetaVal metaVal;

        private Object target;

        private Field field;

        private String signature;

        private Object value;

        public InvokeCell(MetaVal metaVal, Object target, Field field, String value) throws IllegalAccessException {
            this.metaVal = metaVal;
            this.target = target;
            this.field = field;
            field.setAccessible(true);
            signature = target.getClass().getName() + "." + field.getName();
            this.update(value);
        }

        public void update(String value) throws IllegalAccessException {
            this.value = this.metaVal.parser().parse(value);
            field.set(target, this.value);
        }
    }

}
