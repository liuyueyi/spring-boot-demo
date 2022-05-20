package com.git.hui.boot.bind.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将json字符串的配置绑定到List<Jwt>成员变量上
 * @author yihui
 * @date 21/1/17
 */
@Component
@ConfigurationPropertiesBinding
public class ListJwtConverter implements Converter<String, List<Jwt>> {
    @Override
    public List<Jwt> convert(String source) {
        return JSONObject.parseArray(source, Jwt.class);
    }
}
