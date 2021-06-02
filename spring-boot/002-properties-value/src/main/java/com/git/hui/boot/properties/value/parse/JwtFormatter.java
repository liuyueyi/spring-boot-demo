package com.git.hui.boot.properties.value.parse;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.boot.properties.value.model.Jwt;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author yihui
 * @date 2021/6/2
 */
public class JwtFormatter implements Formatter<Jwt> {
    @Override
    public Jwt parse(String text, Locale locale) throws ParseException {
        return Jwt.parse(text, "JwtFormatter");
    }

    @Override
    public String print(Jwt object, Locale locale) {
        return JSONObject.toJSONString(object);
    }
}
