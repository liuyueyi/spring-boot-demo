package com.git.hui.boot.web.order.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by @author yihui in 17:48 19/10/17.
 */
@Order(10)
@Component
//@WebFilter
public class OrderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("order filter!");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
