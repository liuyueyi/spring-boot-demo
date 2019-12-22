package com.git.hui.boot.web.ano;

import com.git.hui.boot.web.constants.PlatformEnum;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 17:57 19/10/8.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Platform {
    PlatformEnum value() default PlatformEnum.ALL;
}
