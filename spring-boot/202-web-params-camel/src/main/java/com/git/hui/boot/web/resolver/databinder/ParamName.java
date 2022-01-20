package com.git.hui.boot.web.resolver.databinder;

import java.lang.annotation.*;

/**
 * @author yihui
 * @data 2022/1/16
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamName {
    /**
     * new name
     */
    String value();
}
