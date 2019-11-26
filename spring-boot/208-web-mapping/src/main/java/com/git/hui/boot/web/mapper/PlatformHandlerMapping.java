package com.git.hui.boot.web.mapper;


import com.git.hui.boot.web.ano.Platform;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Created by @author yihui in 18:05 19/10/8.
 */
public class PlatformHandlerMapping extends RequestMappingHandlerMapping {
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return buildFrom(AnnotationUtils.findAnnotation(handlerType, Platform.class));
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return buildFrom(AnnotationUtils.findAnnotation(method, Platform.class));
    }

    private PlatformRequestCondition buildFrom(Platform platform) {
        return platform == null ? null : new PlatformRequestCondition(platform.value());
    }

}
