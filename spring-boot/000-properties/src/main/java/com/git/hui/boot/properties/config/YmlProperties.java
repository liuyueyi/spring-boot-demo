package com.git.hui.boot.properties.config;

import com.git.hui.boot.properties.config.factory.YamlSourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

/**
 * 读取自定义的yaml文件中的配置信息
 * @author yihui
 * @date 2020/12/24
 */
@Data
@Configuration
@PropertySource(value = {"classpath:biz2.yml"}, factory = YamlSourceFactory.class)
@ConfigurationProperties(prefix = "biz2.yml")
public class YmlProperties {

    private Integer type;

    private String name;

    private List<Map<String, String>> ary;
}
