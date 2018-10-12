package com.git.hui.boot.bean.selfload.engine;

import com.git.hui.boot.bean.selfload.modal.SelfBean;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Set;

/**
 * Created by @author yihui in 09:12 18/9/29.
 */
@Slf4j
public class SelfBeanLoader {

    public static void autoLoadBean(ConfigurableApplicationContext applicationContext) {
        Reflections reflections = new Reflections("com.git.hui");

        // 获取 @SelfBean 标注的接口
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(SelfBean.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            registerBean(applicationContext, aClass.getSimpleName(), aClass);

        }
        log.info("afterPropertiesSet is {}", typesAnnotatedWith);
    }

    private static <T> T registerBean(ConfigurableApplicationContext applicationContext, String name, Class<T> clazz,
            Object... args) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        if (args.length > 0) {
            for (Object arg : args) {
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition(name, beanDefinition);
        return applicationContext.getBean(name, clazz);
    }
}
