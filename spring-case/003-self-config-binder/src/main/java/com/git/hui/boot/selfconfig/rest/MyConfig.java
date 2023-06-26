package com.git.hui.boot.selfconfig.rest;

import com.git.hui.boot.selfconfig.auto.ConfDot;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YiHui
 * @date 2023/6/20
 */
@Data
@Component
@ConfDot(prefix = "config")
public class MyConfig {

    private String user;

    private String pwd;

    private Integer type;

}
