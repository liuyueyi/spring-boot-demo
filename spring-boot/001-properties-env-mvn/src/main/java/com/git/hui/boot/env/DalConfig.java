package com.git.hui.boot.env;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yihui
 * @date 2022/4/20
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DalConfig {
    private String url;

    private String username;

    private String password;
}
