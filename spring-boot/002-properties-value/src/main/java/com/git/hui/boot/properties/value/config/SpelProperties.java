package com.git.hui.boot.properties.value.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author yihui
 * @date 21/6/12
 */
@Data
@Component
public class SpelProperties {

    @Value("1 + 2")
    private String common;

    @Value("demo_${auth.jwt.token}")
    private String prefixConf;

    @Value("#{'abcd'}")
    private String spelStr;

    /**
     * 基本计算
     */
    @Value("#{1 + 2}")
    private String spelVal3;

    /**
     * 列表
     */
    @Value("#{{1, 2, 3}}")
    private List<Integer> spelList;

    /**
     * map
     */
    @Value("#{{a: '123', b: 'cde'}}")
    private Map spelMap;


    /**
     * 嵌套使用，从配置中获取值，然后执行SpEL语句
     */
    @Value("#{'${auth.jwt.token}'.substring(2)}")
    private String spelLen;

    /**
     * 调用静态方法
     */
    @Value("#{T(com.git.hui.boot.properties.value.config.SpelProperties).uuid('${auth.jwt.token}_')}")
    private String spelStaticMethod;

    /**
     * bean 方法访问
     */
    @Value("#{randomService.randUid()}")
    private String spelBeanMethod;

     @Value("${￥{auth.jwt.token}}")
    private String selfProperty;

    public static String uuid(String prefix) {
        return prefix + UUID.randomUUID().toString().replaceAll("_", ".");
    }
}
