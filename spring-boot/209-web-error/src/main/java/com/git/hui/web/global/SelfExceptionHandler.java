package com.git.hui.web.global;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by @author yihui in 18:27 19/10/12.
 */
public class SelfExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        String msg = GlobalExceptionHandler.getThrowableStackInfo(ex);

        try {
            response.addHeader("Content-Type", "text/html; charset=UTF-8");
            response.getWriter().append("自定义异常处理!!! \n").append(msg).flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
