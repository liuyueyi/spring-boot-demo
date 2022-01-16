package com.git.hui.boot.web.resolver;

import java.lang.annotation.*;

import static com.git.hui.boot.web.resolver.CamelType.UNDERLINE_TO_CAMEL;

/**
 * @author yihui
 * @date 2021/8/17
 */

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CamelAno {
    CamelType value() default UNDERLINE_TO_CAMEL;
}
