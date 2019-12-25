package com.git.hui.boot.web.annotation;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 17:49 19/12/24.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {

    /**
     * 版本
     *
     * @return
     */
    String value() default "1.0.0";

}
