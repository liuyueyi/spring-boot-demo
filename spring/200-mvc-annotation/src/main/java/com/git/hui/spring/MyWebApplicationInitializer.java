package com.git.hui.spring;

import com.git.hui.spring.config.RootConfig;
import com.git.hui.spring.config.WebConfig;
import com.git.hui.spring.hook.filter.MyCorsFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;


/**
 * 定义 DispatcherServlet
 * Created by @author yihui in 17:54 19/3/15.
 */
public class MyWebApplicationInitializer extends AbstractDispatcherServletInitializer {
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        //        applicationContext.setConfigLocation("com.git.hui.spring");
        applicationContext.register(RootConfig.class);
        applicationContext.register(WebConfig.class);

        System.out.println("-------------------");

        return applicationContext;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }


    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CharacterEncodingFilter("UTF-8", true), new MyCorsFilter()};
    }
}
