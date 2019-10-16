package com.git.hui.boot.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by @author yihui in 17:17 19/10/12.
 */
@Slf4j
public class OrderFilter implements Filter {

    public OrderFilter() {
        System.out.println("init orderFilter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("order filter ---> ");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
