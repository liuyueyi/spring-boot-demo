package com.git.hui.boot.web.listener.config;

import com.git.hui.boot.web.listener.listener.SelfContextListener;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by @author yihui in 19:50 19/11/21.
 */
@Component
public class ExtendServletConfigInitializer implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(SelfContextListener.class);
    }
}
