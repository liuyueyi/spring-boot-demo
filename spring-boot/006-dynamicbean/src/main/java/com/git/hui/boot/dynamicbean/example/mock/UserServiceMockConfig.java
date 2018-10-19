package com.git.hui.boot.dynamicbean.example.mock;

import com.git.hui.boot.dynamicbean.auto.AutoBean;
import com.git.hui.boot.dynamicbean.auto.AutoFacDIBean;
import com.git.hui.boot.dynamicbean.bean.OriginBean;
import com.git.hui.boot.dynamicbean.example.api.IUserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * Created by @author yihui in 17:06 18/10/16.
 */
@Configuration
public class UserServiceMockConfig implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory)
            throws BeansException {
        // 先删除容器中的Bean定义
        ((DefaultListableBeanFactory) factory).removeBeanDefinition("userService");

        // 创建mock的Bean，并注册到Spring容器
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(IUserService.class, () -> uname -> {
            Random random = new Random();
            return random.nextInt(1024);
        });

        BeanDefinition beanDefinition = builder.getRawBeanDefinition();
        ((DefaultListableBeanFactory) factory).registerBeanDefinition("userService", beanDefinition);
    }
}
