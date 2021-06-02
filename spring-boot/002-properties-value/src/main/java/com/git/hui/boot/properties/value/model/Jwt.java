package com.git.hui.boot.properties.value.model;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/6/2
 */
@Data
public class Jwt {
    private String source;
    private String token;
    private Long expire;

    public static Jwt parse(String text, String source) {
        String[] kvs = StringUtils.split(text, ";");
        Map<String, String> map = new HashMap<>(8);
        for (String kv : kvs) {
            String[] items = StringUtils.split(kv, ":");
            if (items.length != 2) {
                continue;
            }
            map.put(items[0].trim().toLowerCase(), items[1].trim());
        }
        Jwt jwt = new Jwt();
        jwt.setSource(source);
        jwt.setToken(map.get("token"));
        jwt.setExpire(Long.valueOf(map.getOrDefault("expire", "0")));
        return jwt;
    }
}
