package com.git.hui.boot.beanutil.copier;

import cn.hutool.core.lang.Pair;
import com.git.hui.boot.beanutil.copier.util.StrUtil;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yihui
 * @date 2021/4/19
 */
@Component
public class OrikaCopier {
    public <K, T> T copy(K source, Class<T> target) {
        MapperFacade mapper = mapperFactory(source, target).getMapperFacade();
        return mapper.map(source, target);
    }

    private Map<Pair<Class, Class>, MapperFactory> cache = new ConcurrentHashMap<>();

    public MapperFactory mapperFactory(Object source, Class target) {
        Pair<Class, Class> pair = Pair.of(source.getClass(), target);
        if (cache.containsKey(pair)) {
            return cache.get(pair);
        }

        Map<String, String> mapper = buildFieldMapper(source.getClass(), target);
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        ClassMapBuilder builder = mapperFactory.classMap(pair.getKey(), pair.getValue());
        for (Map.Entry<String, String> entry : mapper.entrySet()) {
            builder.field(entry.getKey(), entry.getValue());
        }
        builder.byDefault().register();
        cache.put(pair, mapperFactory);
        return mapperFactory;
    }

    /**
     * 这个是可以做缓存，避免每次重新创建
     *
     * @param source
     * @return
     */
    private Map<String, String> buildFieldMapper(Class source, Class target) {
        PropertyDescriptor[] sourceProperties = ReflectUtils.getBeanProperties(source);

        Set<String> targetProperties = new HashSet<>();
        PropertyDescriptor[] targetPropertyDescriptors = ReflectUtils.getBeanProperties(target);
        for (PropertyDescriptor t : targetPropertyDescriptors) {
            targetProperties.add(t.getName());
        }

        Map<String, String> mapper = new HashMap<>(sourceProperties.length);
        for (PropertyDescriptor s : sourceProperties) {
            String name = s.getName();
            if (targetProperties.contains(name)) {
                continue;
            }

            // 下划线转驼峰
            String camel = StrUtil.toCamelCase(name);
            if (targetProperties.contains(camel)) {
                mapper.put(name, camel);
                continue;
            }

            String under = StrUtil.toUnderCase(name);
            if (targetProperties.contains(under)) {
                mapper.put(name, under);
            }
        }
        return mapper;
    }
}
