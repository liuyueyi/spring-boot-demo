package com.git.hui.boot.web.mapper;

import com.git.hui.boot.web.annotation.Api;
import com.git.hui.boot.web.converter.ApiConverter;
import com.git.hui.boot.web.model.ApiItem;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Created by @author yihui in 17:50 19/12/24.
 */
public class ApiHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return buildFrom(AnnotationUtils.findAnnotation(handlerType, Api.class));
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return buildFrom(AnnotationUtils.findAnnotation(method, Api.class));
    }

    private ApiCondition buildFrom(Api platform) {
        return platform == null ? new ApiCondition(new ApiItem()) :
                new ApiCondition(ApiConverter.convert(platform.value()));
    }

}
