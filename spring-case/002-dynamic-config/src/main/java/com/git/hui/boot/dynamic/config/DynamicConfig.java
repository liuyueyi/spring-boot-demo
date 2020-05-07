package com.git.hui.boot.dynamic.config;

import com.git.hui.boot.dynamic.config.container.MetaContainer;
import com.git.hui.boot.dynamic.config.container.MetaValHolder;
import com.git.hui.boot.dynamic.config.event.MetaChangeListener;
import com.git.hui.boot.dynamic.config.register.MetaValueRegister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 21:18 20/4/21.
 */
@Configuration
public class DynamicConfig {

    @Bean
    @ConditionalOnMissingBean(MetaValHolder.class)
    public MetaValHolder metaValHolder() {
        return key -> null;
    }

    @Bean
    public MetaContainer metaContainer(MetaValHolder metaValHolder) {
        return new MetaContainer(metaValHolder);
    }

    @Bean
    public MetaValueRegister metaValueRegister(MetaContainer metaContainer) {
        return new MetaValueRegister(metaContainer);
    }

    @Bean
    public MetaChangeListener metaChangeListener(MetaContainer metaContainer) {
        return new MetaChangeListener(metaContainer);
    }
}
