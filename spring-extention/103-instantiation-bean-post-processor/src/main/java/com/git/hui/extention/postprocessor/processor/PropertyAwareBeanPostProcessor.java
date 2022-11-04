package com.git.hui.extention.postprocessor.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author YiHui
 * @date 2022/11/4
 */
@Configuration
public class PropertyAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    /**
     * bean实例化之前调用 即new这个bean之前
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (Objects.equals(beanName, "demoService")) {
            System.out.println("[InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation]" + beanName + ">>>bean创建前执行, 即构造方法前");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
    }

    /**
     * 实例化之后调用，即new这个bean之后
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName, "demoService")) {
            System.out.println("[InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation]" + beanName + ">>>bean构造之后执行");
        }
        return true;
    }

    /**
     * 初始化bean之前，相当于把bean注入spring上下文之前
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName, "demoService")) {
            System.out.println("[InstantiationAwareBeanPostProcessor#postProcessBeforeInitialization]" + beanName + ">>>bean初始化前执行，注意此时注入相关已执行，但bean初始化后的钩子尚未触发");
        }
        return bean;
    }

    /**
     * bean 初始化之后
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName, "demoService")) {
            System.out.println("[InstantiationAwareBeanPostProcessor#postProcessAfterInitialization]" + beanName + ">>>初始化之后，此时bean已完全准备完毕");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * bean已经实例化完成，在属性注入时触发，如 @Value, @Autowired, @Resource
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName, "demoService")) {
            System.out.println("[InstantiationAwareBeanPostProcessor]" + beanName + ">>>设置注入属性时执行 =>" + pvs);
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
    }
}
