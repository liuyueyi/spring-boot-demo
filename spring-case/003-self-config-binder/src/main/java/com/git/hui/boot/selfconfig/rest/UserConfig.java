package com.git.hui.boot.selfconfig.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YiHui
 * @date 2023/11/7
 */
@Data
@Component
@ConfigurationProperties(prefix = "config")
public class UserConfig {
    private String user;

    private String pwd;

    private Integer type;

    private String wechat;
}
