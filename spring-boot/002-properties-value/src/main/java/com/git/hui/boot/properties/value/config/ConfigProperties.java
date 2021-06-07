package com.git.hui.boot.properties.value.config;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.boot.properties.value.model.Jwt;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author yihui
 * @date 2021/6/2
 */
@Data
@Component
public class ConfigProperties {

    @Value("${auth.jwt.token}")
    private String token;

    @Value("${auth.jwt.expire}")
    private Long expire;

    /**
     * 不存在，使用默认值
     */
    @Value("${auth.jwt.no:default_no:111}")
    private String no;

    /**
     * 英文逗号分隔，转列表
     */
    @Value("${auth.jwt.whiteList}")
    private List<Long> whiteList;

    /**
     * yml数组，无法转换过来，只能根据 "auth.jwt.blackList[0]", "auth.jwt.blackList[1]" 来取对应的值
     */
    @Value("${auth.jwt.blackList:10,11,12}")
    private String[] blackList;

    /**
     * 借助 PropertyEditor 来实现字符串转对象
     */
    @Value("${auth.jwt.tt}")
    private Jwt tt;

    @Value("1+2")
    private String a;

    @Value("#{1+2}")
    private String b;

    /**
     * 配置注入 + 前缀
     */
    @Value("prefix_${auth.jwt.token}")
    private String c;

    /**
     * spel表达式
     */
    @Value("#{randomService.randUid()}")
    private String rid;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        System.out.println("token: " + token + "\nexpire:" + expire + "\nno:" + no + "\nwhiteList:" + whiteList +
                "\nblackList:" + Arrays.asList(blackList) + "\ntt:" + tt);
    }

    public String toJsonStr() {
        return JSONObject.toJSONString(this);
    }
}
