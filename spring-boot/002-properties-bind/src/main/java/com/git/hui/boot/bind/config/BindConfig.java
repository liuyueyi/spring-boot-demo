package com.git.hui.boot.bind.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

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
}
