package com.git.hui.boot.properties.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 18:51 18/9/21.
 */
@Data
@RefreshScope
@Component
public class ValueConfig {
    @Value("${rest.uuid}")
    private String uuid;
}
