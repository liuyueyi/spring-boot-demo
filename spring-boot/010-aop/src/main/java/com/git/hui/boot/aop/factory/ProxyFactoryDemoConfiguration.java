package com.git.hui.boot.aop.factory;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by @author yihui in 09:37 19/7/10.
 */
@Configuration
public class ProxyFactoryDemoConfiguration {
    @Bean
    public DemoService demoService() {
        return new DemoService();
    }

    @Bean
    @Primary
    public ProxyFactoryBean proxyFactoryBean(AnnotationConfigApplicationContext applicationContext, DemoService demoService)
            throws ClassNotFoundException {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setBeanFactory(applicationContext.getBeanFactory());
        // 被代理的接口
        proxyFactoryBean.setProxyInterfaces(new Class[]{DemoInter.class});
        // aop
        proxyFactoryBean.setInterceptorNames("proxyDemoAdvice");
        proxyFactoryBean.setTarget(demoService);
        return proxyFactoryBean;
    }
}
