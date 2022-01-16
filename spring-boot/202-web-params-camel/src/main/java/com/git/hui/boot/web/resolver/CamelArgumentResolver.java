package com.git.hui.boot.web.resolver;

import com.sun.javaws.jnl.RContentDesc;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;

/**
 * @author yihui
 * @date 2021/8/17
 */
public class CamelArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 用于判断当前的参数
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterAnnotation(CamelAno.class) != null) {
            return true;
        }

        if (methodParameter.getMethodAnnotation(CamelAno.class) != null) {
            return true;
        }
        return false;
    }

    /**
     * 参数解析
     *
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        CamelAno camelAno = methodParameter.getParameterAnnotation(CamelAno.class);
        if ( camelAno == null) {
            camelAno = methodParameter.getMethodAnnotation(CamelAno.class);
        }
        Assert.isTrue(camelAno != null, "参数适配异常");
        CamelType camelType = camelAno.value();
        String bindParamName = getBindParamName(methodParameter);
        String val = getStrVal(bindParamName, nativeWebRequest, camelType);

        Object ans;
        if (webDataBinderFactory != null) {
            WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, (Object)null, bindParamName);

            try {
                ans = binder.convertIfNecessary(val, methodParameter.getParameterType(), methodParameter);
            } catch (ConversionNotSupportedException var11) {
                throw new MethodArgumentConversionNotSupportedException(val, var11.getRequiredType(), bindParamName, methodParameter, var11.getCause());
            } catch (TypeMismatchException var12) {
                throw new MethodArgumentTypeMismatchException(val, var12.getRequiredType(), bindParamName, methodParameter, var12.getCause());
            }
            return ans;
        }
        return val;
    }

    private String getBindParamName(MethodParameter methodParameter) {
        String paramName = null;
        if (methodParameter.getParameterAnnotation(RequestParam.class) != null) {
            // 通过注解指定参数name，则直接使用它
            RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);
            paramName = StringUtils.isEmpty(requestParam.name()) ? requestParam.value(): requestParam.name();
        }
        if (StringUtils.isEmpty(paramName)) {
            // 未特殊指定时，绑定的参数name为参数名
            return methodParameter.getParameterName();
        }
        return paramName;
    }

    private String getStrVal(String paramName, NativeWebRequest webRequest, CamelType camelType) {
        String val = webRequest.getParameter(paramName);
        if (val != null) {
            return val;
        }

        switch (camelType) {
            case UNDERLINE_TO_CAMEL:
                // 传参下划线
                return webRequest.getParameter(CamelUtil.toUnderCase(paramName));
            case CAMEL_TO_UNDERLINE:
                // 传参驼峰
                return webRequest.getParameter(CamelUtil.toCamelCase(paramName));
            default:
                // 传参都有可能
                String newParam = CamelUtil.toCamelCase(paramName);
                if (paramName.equals(newParam)) {
                    newParam = CamelUtil.toUnderCase(paramName);
                }
                return webRequest.getParameter(newParam);
        }
    }

    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        ServletRequest servletRequest = (ServletRequest)request.getNativeRequest(ServletRequest.class);
        Assert.state(servletRequest != null, "No ServletRequest");
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder)binder;
        servletBinder.bind(servletRequest);
    }
}
