package com.git.hui.boot.web.resolver;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 18:06 19/8/23.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ListParam {
    /**
     * Alias for {@link #name}.
     */
    @AliasFor("name") String value() default "";

    /**
     * The name of the request parameter to bind to.
     *
     * @since 4.2
     */
    @AliasFor("value") String name() default "";

}
