package com.git.hui.boot.web.filter;

import com.git.hui.boot.web.rest.DemoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by @author yihui in 16:45 19/10/12.
 */
@Slf4j
@Order(2)
@WebFilter
public class AuthFilter implements Filter, Ordered {
    @Autowired
    private DemoBean demoBean;

    public AuthFilter() {
        System.out.println("init autFilter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("in auth filter! {}", demoBean);
        // 测试，用header中的 tx-demo 来判断是否为认证的请求
        HttpServletRequest req = (HttpServletRequest) request;
        String auth = req.getHeader("tx-demo");
        if ("yihuihui".equals(auth)) {
            // 只有认证的请求才允许访问，请求头中没有这个时，不执行下面的的方法，则表示请求被过滤了
            // 在测试优先级时打开下面的注释
            // chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getOrder() {
        return 2;
    }
}
