package com.git.hui.extention.beanfactoryaware.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

/**
 * @author YiHui
 * @date 2023/2/13
 */
@Service
public class BeanCacheService implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("bean factory 初始化!");

        DemoService demoService = this.beanFactory.getBean(DemoService.class);
        demoService.setPrefix("cache");

        TestService testService = this.beanFactory.getBean(TestService.class);
        testService.setPrefix("cache");
    }

    public <T> T findBean(Class<T> bean) {
        return beanFactory.getBean(bean);
    }
}
