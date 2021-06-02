package com.git.hui.boot.bind.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * @author yihui
 * @date 21/1/16
 */
@Data
@Validated
@ConfigurationProperties(prefix = "hhui.bind", ignoreInvalidFields = true, ignoreUnknownFields = false)
public class BindConfig {

    private String name;

    @Min(13)
    @Max(66)
    private Integer age = 18;

    private List<String> list;

    private Map<String, String> map;

    private Pwd mainPwd;

    private Jwt jwt;

    /**
     * fixme 对于列表这种嵌套的方式，生成的 spring-configuration-metadata.json 无法友好的支持它； 即IDEA中，在yml文件中，配置对应的属性时，无法直接定位到 Jwt#token
     */
    private List<Jwt> tokens;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        System.out.println(tokens);
    }
}
