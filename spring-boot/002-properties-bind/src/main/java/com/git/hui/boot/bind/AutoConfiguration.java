package com.git.hui.boot.bind;

import com.git.hui.boot.bind.config.BindConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yihui
 * @date 21/1/16
 */
@EnableConfigurationProperties({BindConfig.class})
@Configuration
public class AutoConfiguration {
}
