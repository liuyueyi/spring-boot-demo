package com.git.hui.boot.multi.datasource.dynamic.ano;

import java.lang.annotation.*;

/**
 * @author yihui
 * @date 21/1/9
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DS {
    String value() default "";
}
