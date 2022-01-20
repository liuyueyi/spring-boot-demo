package com.git.hui.boot.web.resolver.databinder;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于注解类 @ParamName 实现的参数别名映射，适用于注解放在方法传参上，参数为简单类型
 *
 * @author yihui
 * @data 2022/1/16
 */
public class ParamArgumentProcessor extends RequestParamMethodArgumentResolver {
    public ParamArgumentProcessor() {
        super(true);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ParamName.class) && BeanUtils.isSimpleProperty(parameter.getParameterType());
    }


    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        ParamName paramName = parameter.getParameterAnnotation(ParamName.class);
        String ans = request.getParameter(paramName.value());
        if (ans == null) {
            return request.getParameter(name);
        }
        return ans;
    }
}
