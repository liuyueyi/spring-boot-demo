package com.git.hui.boot.web.interceptor.inter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yihui
 * @date 21/8/2
 */
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    /**
     * 在执行具体的Controller方法之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 一个简单的安全校验，要求请求头中必须包含 req-name : yihuihui
        String header = request.getHeader("req-name");
        if ("yihuihui".equals(header)) {
            return true;
        }

        log.info("请求头错误: {}", header);
        return false;
    }

    /**
     * controller执行完毕之后被调用，在 DispatcherServlet 进行视图返回渲染之前被调用，
     * 所以我们可以在这个方法中对 Controller 处理之后的 ModelAndView 对象进行操作。
     *
     * preHandler 返回false，这个也不会执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("执行完毕!");
        response.setHeader("res", "postHandler");
    }


    /**
     * 方法需要在当前对应的 Interceptor 类的 preHandle 方法返回值为 true 时才会执行。
     * 顾名思义，该方法将在整个请求结束之后，也就是在 DispatcherServlet 渲染了对应的视图之后执行。此方法主要用来进行资源清理。
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("回收");
    }
}
