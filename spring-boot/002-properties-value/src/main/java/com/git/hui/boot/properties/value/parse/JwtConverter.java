package com.git.hui.boot.properties.value.parse;

import com.git.hui.boot.properties.value.model.Jwt;
import org.springframework.core.convert.converter.Converter;

/**
 * convert 优先级大于 propertyEditor
 *
 * @author yihui
 * @date 2021/6/2
 */
public class JwtConverter implements Converter<String, Jwt> {
    @Override
    public Jwt convert(String s) {
        return Jwt.parse(s, "JwtConverter");
    }
}
