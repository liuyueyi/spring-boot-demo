package com.git.hui.extention.postprocessor.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author YiHui
 * @date 2022/11/4
 */
@Configuration
public class BasicBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("userService");
        beanDefinition.getPropertyValues().addPropertyValue("inject", "随机插入:" + UUID.randomUUID().toString());
    }
}
