package com.git.hui.boot.web.util;

import lombok.Setter;
import org.springframework.core.env.Environment;

/**
 * @author yihui
 * @date 2021/3/8
 */
public class EnvironmentUtil {
    @Setter
    private static Environment environment;

    public static String getProperties(String key) {
        return environment.getProperty(key);
    }
}
