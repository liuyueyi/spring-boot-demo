package com.git.hui.boot.selfconfig.auto;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的配置工厂类，专门用于 ConfDot 属性配置文件的配置加载，支持从自定义的配置源获取
 *
 * @author YiHui
 * @date 2023/6/20
 */
@Component
public class SelfConfigContainer implements EnvironmentAware, ApplicationContextAware {
    private ConfigurableEnvironment environment;
    private ApplicationContext applicationContext;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private SelfConfigBinder binder;
    public Map<String, Object> configCache = new HashMap<>();

    @PostConstruct
    public void init() {
        configCache.put("config.type", 12);
        configCache.put("config.wechat", "一灰灰blog");
        bindBeansFromLocalCache("config", configCache);
    }

    private void bindBeansFromLocalCache(String namespace, Map<String, Object> cache) {
        MutablePropertySources propertySources = new MutablePropertySources(environment.getPropertySources());
        // 将内存的配置信息设置为最高优先级
        MapPropertySource propertySource = new MapPropertySource(namespace, cache);
        propertySources.addFirst(propertySource);
        this.binder = new SelfConfigBinder(this.applicationContext, propertySources);
        refreshConfig(null, null);
    }

    /**
     * 配置绑定
     *
     * @param bindable
     */
    public void bind(Bindable bindable) {
        binder.bind(bindable);
    }

    /**
     * 支持配置的动态刷新
     *
     * @param key
     * @param val
     */
    public void refreshConfig(String key, String val) {
        if (key != null) {
            configCache.put(key, val);
        }
        applicationContext.getBeansWithAnnotation(ConfDot.class).values().forEach(bean -> {
            Bindable<?> target = Bindable.ofInstance(bean)
                    // Bindable.of(ResolvableType.forClass(bean.getClass())).withExistingValue(bean)
                    .withAnnotations(AnnotationUtils.findAnnotation(bean.getClass(), ConfDot.class));
            bind(target);
        });
    }
}
