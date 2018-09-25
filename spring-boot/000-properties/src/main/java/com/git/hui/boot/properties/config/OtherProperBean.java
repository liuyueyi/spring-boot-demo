package com.git.hui.boot.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by @author yihui in 15:08 18/9/19.
 */
@Data
@Configuration
@PropertySource({"classpath:biz.properties"})
@ConfigurationProperties(prefix = "biz")
public class OtherProperBean {
    private String token;
    private String appKey;
    private Integer appVersion;
    private String source;
    private String uuid;
}
