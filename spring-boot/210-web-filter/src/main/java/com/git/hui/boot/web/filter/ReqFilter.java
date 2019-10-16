package com.git.hui.boot.web.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 说明： @Order 注解， Ordered 接口的实现，都不代表这个filter的初始化顺序； 这两个也无法指定Filter的优先级
 * Created by @author yihui in 16:35 19/10/12.
 */
@Slf4j
@Order(1)
@WebFilter
public class ReqFilter implements Filter, Ordered {

    public ReqFilter() {
        System.out.println("init reqFilter");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("url={}, params={}", req.getRequestURI(), JSON.toJSONString(req.getParameterMap()));
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
