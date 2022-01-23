package com.git.hui.boot.web.resolver.databinder;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于注解类 @ParamName 实现的参数别名映射
 *
 * @author yihui
 * @data 2022/1/16
 */
public class ParamAttrProcessor extends ServletModelAttributeMethodProcessor {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    //Rename cache
    private final Map<Class<?>, Map<String, String>> replaceMap = new ConcurrentHashMap<>();

    public ParamAttrProcessor() {
        super(true);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!BeanUtils.isSimpleProperty(parameter.getParameterType())) {
            for (Field field: parameter.getParameterType().getDeclaredFields()) {
                if (field.getDeclaredAnnotation(ParamName.class) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest nativeWebRequest) {
        Object target = binder.getTarget();
        Class<?> targetClass = target.getClass();
        if (!replaceMap.containsKey(targetClass)) {
            Map<String, String> mapping = analyzeClass(targetClass);
            replaceMap.put(targetClass, mapping);
        }
        Map<String, String> mapping = replaceMap.get(targetClass);
        ParamDataBinder paramNameDataBinder = new ParamDataBinder(target, binder.getObjectName(), mapping);
//        requestMappingHandlerAdapter.getWebBindingInitializer().initBinder(paramNameDataBinder);
        super.bindRequestParameters(paramNameDataBinder, nativeWebRequest);
    }

    private static Map<String, String> analyzeClass(Class<?> targetClass) {
        Field[] fields = targetClass.getDeclaredFields();
        Map<String, String> renameMap = new HashMap<>();
        for (Field field : fields) {
            ParamName paramNameAnnotation = field.getAnnotation(ParamName.class);
            if (paramNameAnnotation != null && !paramNameAnnotation.value().isEmpty()) {
                renameMap.put(paramNameAnnotation.value(), field.getName());
            }
        }
        if (renameMap.isEmpty()) return Collections.emptyMap();
        return renameMap;
    }
}
