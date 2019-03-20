package com.git.hui.spring.hook.intercept;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，我们要求必须传入referer
 * Created by @author yihui in 16:13 19/3/18.
 */
public class SecurityIntercept implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String referer = request.getHeader("referer");
        if (StringUtils.isEmpty(referer) || !referer.startsWith("http://hhui.top")) {
            return false;
        }

        return true;
    }
}
