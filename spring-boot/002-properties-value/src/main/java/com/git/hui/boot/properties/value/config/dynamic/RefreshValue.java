package com.git.hui.boot.properties.value.config.dynamic;

import java.lang.annotation.*;

/**
 * @author yihui
 * @date 2021/6/7
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshValue {
}
