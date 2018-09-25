package com.git.hui.boot.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:37 18/9/21.
 */
@Data
@Component
@ConfigurationProperties(prefix = "biz")
public class BizConfig {
    private String key;
    private Long refresh;
}
