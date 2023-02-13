package com.git.hui.extention.beanfactoryaware.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YiHui
 * @date 2023/2/13
 */
@Component
@ConfigurationProperties(prefix = "user")
public class MyAutoConfig {
    @Getter
    @Setter
    private String data;
}
