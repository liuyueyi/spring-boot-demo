package com.git.hui.boot.bind.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 21/1/17
 */
@Component
@ConfigurationPropertiesBinding
public class JwtConverter implements Converter<String, Jwt> {
    @Override
    public Jwt convert(String source) {
        return JSONObject.parseObject(source, Jwt.class);
    }
}
