package com.git.hui.boot.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 14:28 18/9/19.
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.proper")
public class ProperBean {
    private String key;
    private Integer id;
    private String value;
}
