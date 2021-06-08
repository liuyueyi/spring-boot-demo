package com.git.hui.boot.properties.value.config.dynamic;

import com.git.hui.boot.properties.value.config.MyPropertySourcesPlaceHolderConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.context.support.PropertySourcesPlaceholderConfigurer.ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME;

/**
 * @author yihui
 * @date 2021/6/7
 */
@RestController
public class DynamicRest {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ConfigurableEnvironment environment;
    @Autowired
    RefreshConfigProperties refreshConfigProperties;
    @Autowired
    MyPropertySourcesPlaceHolderConfigure myPropertySourcesPlaceHolderConfigure;

    @GetMapping(path = "dynamic/update")
    public RefreshConfigProperties updateEnvironment(String key, String value) {
        String name = "applicationConfig: [classpath:/application-dynamic.yml]";
        MapPropertySource propertySource = (MapPropertySource) environment.getPropertySources().get(name);
        Map<String, Object> source = propertySource.getSource();
        Map<String, Object> map = new HashMap<>(source.size());
        map.putAll(source);
        map.put(key, value);
        environment.getPropertySources().replace(name, new MapPropertySource(name, map));

        applicationContext.publishEvent(new AnoValueRefreshPostProcessor.ConfigUpdateEvent(this, key));
        return refreshConfigProperties;
    }

    @GetMapping(path = "dynamic/show")
    public RefreshConfigProperties show() {
        return refreshConfigProperties;
    }

    @GetMapping(path = "dynamic/property")
    public String propertyTest() {
        Object obj = myPropertySourcesPlaceHolderConfigure.getAppliedPropertySources()
                .get(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME).getProperty("xhh.dynamic.name");
        System.out.println(obj);
        return String.valueOf(obj);
    }
}
