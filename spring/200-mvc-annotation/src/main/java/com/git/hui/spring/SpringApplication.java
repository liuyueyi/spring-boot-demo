package com.git.hui.spring;

import com.git.hui.spring.config.RootConfig;
import com.git.hui.spring.config.WebConfig;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * Created by @author yihui in 17:58 19/3/15.
 */
public class SpringApplication {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        // 服务器根目录，类似于tomcat部署的项目。 完整的访问路径为ip:port/contextPath/realRequestMapping
        //ip:port/项目路径/api请求路径
        handler.setContextPath("/");

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebConfig.class);
        applicationContext.register(RootConfig.class);

        //相当于web.xml中配置的ContextLoaderListener
        handler.addEventListener(new ContextLoaderListener(applicationContext));

        //springmvc拦截规则 相当于web.xml中配置的DispatcherServlet
        handler.addServlet(new ServletHolder(new DispatcherServlet(applicationContext)), "/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
