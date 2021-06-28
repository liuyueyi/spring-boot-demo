package com.git.hui.boot.web.anno;

import java.lang.annotation.*;

/**
 * @author yihui
 * @date 2021/3/5
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {
}
