package com.git.hui.boot.autoinject.ano;

import java.lang.annotation.*;

/**
 * @author yihui
 * @date 2021/2/9
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoInject {
}
