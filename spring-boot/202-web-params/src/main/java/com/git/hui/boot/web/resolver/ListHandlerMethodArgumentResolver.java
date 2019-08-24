package com.git.hui.boot.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

/**
 * Created by @author yihui in 17:43 19/8/23.
 */
public class ListHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ListParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ListParam param = parameter.getParameterAnnotation(ListParam.class);
        if (param == null) {
            throw new IllegalArgumentException(
                    "Unknown parameter type [" + parameter.getParameterType().getName() + "]");
        }

        String name = "".equalsIgnoreCase(param.name()) ? param.value() : param.name();
        if ("".equalsIgnoreCase(name)) {
            name = parameter.getParameter().getName();
        }
        String ans = webRequest.getParameter(name);
        if (ans == null) {
            return null;
        }

        String[] cells = StringUtils.split(ans, ",");
        return Arrays.asList(cells);
    }
}
