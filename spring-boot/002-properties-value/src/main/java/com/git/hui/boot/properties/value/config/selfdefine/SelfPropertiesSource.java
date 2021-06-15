package com.git.hui.boot.properties.value.config.selfdefine;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yihui
 * @date 21/6/12
 */
@Component
public class SelfPropertiesSource extends MapPropertySource {
    @Resource
    private ConfigurableEnvironment environment;

    public SelfPropertiesSource(String name, Map<String, Object> source) {
        super(name, source);
    }

    public SelfPropertiesSource() {
        this("SelfPropertiesSource", new HashMap<>());
    }

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(this);
    }

    @Override
    public Object getProperty(String name) {
        if (name.startsWith("￥") && name.endsWith("}")) {
            name = name.substring(2, name.length() - 2);
            return super.getProperty(name) + " (一灰灰blog) ";
        }
        return super.getProperty(name);
    }
}
