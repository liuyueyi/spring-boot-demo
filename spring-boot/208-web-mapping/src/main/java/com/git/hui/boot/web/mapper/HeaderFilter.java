package com.git.hui.boot.web.mapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by @author yihui in 19:48 19/10/8.
 */
@WebFilter(urlPatterns = "/*")
public class HeaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String platform = httpServletRequest.getHeader("x-platform");
        if (platform == null || platform.length() == 0 || !PlatformRequestCondition.enable.containsKey(platform)) {
            httpServletRequest.setAttribute("x-platform", "all");
        } else {
            httpServletRequest.setAttribute("x-platform", platform);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
