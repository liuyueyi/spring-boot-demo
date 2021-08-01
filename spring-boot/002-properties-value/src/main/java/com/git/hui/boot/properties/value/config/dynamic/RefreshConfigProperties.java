package com.git.hui.boot.properties.value.config.dynamic;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 演示动态配置刷新
 *
 * @author yihui
 * @date 2021/6/7
 */
@Data
@Component
@RefreshValue
public class RefreshConfigProperties {

    @Value("${xhh.dynamic.name}")
    private String name;

    @Value("${xhh.dynamic.age:18}")
    private Integer age;

    @Value("hello ${xhh.dynamic.other:test}")
    private String other;

}
