package com.git.hui.boot.selfconfig.auto;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.ResolvableType;
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
 * 自定义的配置工厂类，专门用于 ConfigurationProperties 属性配置文件的配置加载，支持从自定义的配置源获取
 *
 * @author YiHui
 * @date 2023/6/20
 */
@Component
public class SelfConfigFactory implements EnvironmentAware, ApplicationContextAware {
    private ConfigurableEnvironment environment;
    private ApplicationContext applicationContext;
    public Map<String, Object> cache = new HashMap<>();

    private SelfConfigBinder binder;

    @PostConstruct
    public void init() {
        cache.put("config.type", 12);
        bindBeansFromLocalCache("config", cache);
    }

    private void bindBeansFromLocalCache(String namespace, Map<String, Object> cache) {
        MutablePropertySources propertySources = new MutablePropertySources(environment.getPropertySources());
        // 将内存的配置信息设置为最高优先级
        MapPropertySource propertySource = new MapPropertySource(namespace, cache);
        propertySources.addFirst(propertySource);
        this.binder = new SelfConfigBinder(this.applicationContext, propertySources);
        applicationContext.getBeansWithAnnotation(ConfigurationProperties.class).values().forEach(bean -> {
            Bindable<?> target = Bindable.ofInstance(bean)
//                    Bindable.of(ResolvableType.forClass(bean.getClass())).withExistingValue(bean)
                    .withAnnotations(AnnotationUtils.findAnnotation(bean.getClass(), ConfigurationProperties.class));
            binder.bind(target);
        });
    }

    public void bind(Bindable bindable) {
        binder.bind(bindable);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
