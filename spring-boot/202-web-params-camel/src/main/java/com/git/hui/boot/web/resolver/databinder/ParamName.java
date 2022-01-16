package com.git.hui.boot.web.resolver.databinder;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * @author yihui
 * @data 2022/1/16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamName {
    /**
     * new name
     */
    String value();
}
