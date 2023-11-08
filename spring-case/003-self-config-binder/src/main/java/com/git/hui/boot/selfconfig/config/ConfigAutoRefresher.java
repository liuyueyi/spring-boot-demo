package com.git.hui.boot.selfconfig.config;

import com.git.hui.boot.selfconfig.property.ConfigChangeListener;
import com.git.hui.boot.selfconfig.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.bind.handler.IgnoreErrorsBindHandler;
import org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler;
import org.springframework.boot.context.properties.bind.handler.NoUnboundElementsBindHandler;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.UnboundElementsSourceFilter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * 支持 @ConfigurationProperties 绑定的配置动态刷新
 *
 * @author YiHui
 * @date 2023/11/7
 */
@Slf4j
@Component
public class ConfigAutoRefresher implements ApplicationRunner {
    private volatile Binder binder;

    public ConfigAutoRefresher() {
        System.out.println("初始化");
    }

    /**
     * 配置变更之后的刷新
     */
    @EventListener
    public void refreshConfig(ConfigChangeListener.ConfigChangeEvent event) {
        log.info("配置发生变更，开始动态刷新: {}", event);
        SpringUtil.getApplicationContext().getBeansWithAnnotation(ConfigurationProperties.class).values().forEach(bean -> {
            Bindable<?> target = Bindable.ofInstance(bean).withAnnotations(AnnotationUtils.findAnnotation(bean.getClass(), ConfigurationProperties.class));
            bind(target);
        });
    }

    /**
     * 重新绑定bean对象对应的配置值
     *
     * @param bindable
     * @param <T>
     */
    public <T> void bind(Bindable<T> bindable) {
        ConfigurationProperties propertiesAno = bindable.getAnnotation(ConfigurationProperties.class);
        if (propertiesAno != null) {
            BindHandler bindHandler = getBindHandler(propertiesAno);
            this.binder.bind(propertiesAno.prefix(), bindable, bindHandler);
        }
    }

    private BindHandler getBindHandler(ConfigurationProperties annotation) {
        BindHandler handler = new IgnoreTopLevelConverterNotFoundBindHandler();
        if (annotation.ignoreInvalidFields()) {
            handler = new IgnoreErrorsBindHandler(handler);
        }
        if (!annotation.ignoreUnknownFields()) {
            UnboundElementsSourceFilter filter = new UnboundElementsSourceFilter();
            handler = new NoUnboundElementsBindHandler(handler, filter);
        }
        return handler;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("初始化!");
        ConfigurableEnvironment environment = (ConfigurableEnvironment) SpringUtil.getEnvironment();
        this.binder = new Binder(ConfigurationPropertySources.from(environment.getPropertySources()),
                new PropertySourcesPlaceholdersResolver(environment),
                new DefaultConversionService(),
                ((ConfigurableApplicationContext) SpringUtil.getApplicationContext())
                        .getBeanFactory()::copyRegisteredEditorsTo);
    }
}
