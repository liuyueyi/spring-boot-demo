package com.git.hui.spring.hook.intercept;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，我们要求必须传入referer
 * Created by @author yihui in 16:13 19/3/18.
 *
 * 测试方法：
 *
 *   - 带上referer，正常执行   curl -i 'http://127.0.0.1:8080/welcome?name=一灰灰' -e 'http://hhui.top'
 *   - 不指定referer，无法传递到Controller  curl -i 'http://127.0.0.1:8080/welcome?name=一灰灰'
 *
 */
@Component
public class MyRefererInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("------- in my referer interceptor -----------");
        String referer = request.getHeader("referer");
        if (StringUtils.isEmpty(referer) || !referer.startsWith("http://hhui.top")) {
            return false;
        }

        return true;
    }
}
