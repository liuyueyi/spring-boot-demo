package com.git.hui.boot.config.selector.printcase;

import com.git.hui.boot.config.selector.printcase.config.PrintConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 18:57 19/12/13.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PrintConfigSelector.class)
public @interface PrintSelector {
    Class<?> value() default PrintConfigSelector.ConsoleConfiguration.class;
}
