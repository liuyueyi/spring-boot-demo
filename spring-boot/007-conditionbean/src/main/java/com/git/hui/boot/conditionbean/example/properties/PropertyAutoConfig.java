package com.git.hui.boot.conditionbean.example.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by @author yihui in 09:57 18/10/19.
 */
@Configuration
public class PropertyAutoConfig {
    private Environment environment;

    public PropertyAutoConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * 配置存在时才会加载这个bean
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty("conditional.property")
    public PropertyExistBean propertyExistBean() {
        return new PropertyExistBean(environment.getProperty("conditional.property"));
    }

    /**
     * 即便配置不存在时，也可以加载这个bean
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "conditional.property.no", matchIfMissing = true)
    public PropertyNotExistBean propertyNotExistBean() {
        return new PropertyNotExistBean("conditional.property");
    }

    @Bean
    @ConditionalOnProperty(name = {"conditional.property"}, havingValue = "properExists")
    public PropertyValueExistBean propertyValueExistBean() {
        return new PropertyValueExistBean("properExists");
    }

    @Bean
    @ConditionalOnProperty(name = {"conditional.property"}, havingValue = "properNotExists")
    public PropertyValueNotExistBean propertyValueNotExistBean() {
        return new PropertyValueNotExistBean("properNotExists");
    }
}
