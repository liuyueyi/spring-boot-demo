package com.git.hui.boot.properties.value.config;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author yihui
 * @date 2021/6/2
 */
@Primary
@Component
public class MyPropertySourcesPlaceHolderConfigure extends PropertySourcesPlaceholderConfigurer {
    @Autowired
    protected Environment environment;

    /**
     * {@code PropertySources} from the given {@link Environment}
     * will be searched when replacing ${...} placeholders.
     *
     * @see #setPropertySources
     * @see #postProcessBeanFactory
     */
    @Override
    public void setEnvironment(Environment environment) {
        super.setEnvironment(environment);
        this.environment = environment;
    }

    @SneakyThrows
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
        Field field = propertyResolver.getClass().getDeclaredField("propertySources");
        boolean access = field.isAccessible();
        field.setAccessible(true);
        MutablePropertySources propertySource = (MutablePropertySources) field.get(propertyResolver);
        field.setAccessible(access);
        PropertySource source = new PropertySource<Environment>(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME, this.environment) {
            @Override
            @Nullable
            public String getProperty(String key) {
                // 对数组进行兼容
                String ans = this.source.getProperty(key);
                if (ans != null) {
                    return ans;
                }

                StringBuilder builder = new StringBuilder();
                String prefix = key.contains(":") ? key.substring(key.indexOf(":")) : key;
                int i = 0;
                while (true) {
                    String subKey = prefix + "[" + i + "]";
                    ans = this.source.getProperty(subKey);
                    if (ans == null) {
                        return i == 0 ? null : builder.toString();
                    }

                    if (i > 0) {
                        builder.append(",");
                    }
                    builder.append(ans);
                    ++i;
                }
            }
        };
        propertySource.addLast(source);
        super.processProperties(beanFactoryToProcess, propertyResolver);
    }
}
