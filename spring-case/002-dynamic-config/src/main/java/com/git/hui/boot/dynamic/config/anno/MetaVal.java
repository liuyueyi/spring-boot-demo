package com.git.hui.boot.dynamic.config.anno;

import com.git.hui.boot.dynamic.config.parser.MetaParser;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 21:19 20/4/21.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MetaVal {

    /**
     * 获取配置的规则
     *
     * @return
     */
    String value() default "";

    /**
     * meta value转换目标对象；目前提供基本数据类型支持
     *
     * @return
     */
    MetaParser parser() default MetaParser.STRING_PARSER;
}
