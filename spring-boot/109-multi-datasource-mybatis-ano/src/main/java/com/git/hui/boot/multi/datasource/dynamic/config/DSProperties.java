package com.git.hui.boot.multi.datasource.dynamic.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author yihui
 * @date 21/1/9
 */
@Data
@ConfigurationProperties(prefix = "spring.dynamic")
public class DSProperties {
    private Map<String, DataSourceProperties> datasource;
}
