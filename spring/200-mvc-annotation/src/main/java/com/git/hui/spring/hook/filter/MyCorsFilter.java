package com.git.hui.spring.hook.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by @author yihui in 16:13 19/3/18.
 *
 * 测试:
 *
 * - 返回头不会包含CORS相关： curl -i 'http://127.0.0.1:8080/welcome?name=一灰灰' -e 'http://hhui.top'
 * - 返回头支持CORS： curl -i 'http://127.0.0.1:8080/hello?name=一灰灰' -e 'http://hhui.top'
 */
@Slf4j
public class MyCorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            if ("/hello".equals(request.getRequestURI())) {
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
                response.setHeader("Access-Control-Allow-Methods", "*");
                response.setHeader("Access-Control-Allow-Credentials", "true");
            }
        } finally {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
