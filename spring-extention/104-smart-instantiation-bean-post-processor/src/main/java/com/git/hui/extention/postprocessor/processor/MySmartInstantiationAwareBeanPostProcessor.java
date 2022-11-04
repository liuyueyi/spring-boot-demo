package com.git.hui.extention.postprocessor.processor;

import com.git.hui.extention.postprocessor.service.DemoService2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * 同样是 InstantiationAwareBeanPostProcessor 的子类，会继承它的五个方法，此外还扩展了三个
 *
 * @author YiHui
 * @date 2022/11/4
 */
@Configuration
public class MySmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    /**
     * 该触发点发生在postProcessBeforeInstantiation之前，通常适用于预测bean的类型，返回第一个预测成功的Class类型
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
//        if (Objects.equals(beanName, "demoService2"))
//            System.out.println("[MySmartInstantiationAwareBeanPostProcessor#predictBeanType] bean实例化前，用于预测bean类型:" + beanClass);
        return SmartInstantiationAwareBeanPostProcessor.super.predictBeanType(beanClass, beanName);
    }

    /**
     * 在postProcessBeforeInstantiation之后，在new bean()之前，返回所有的构造方法，可用于自定义选择bean对象的构造器；
     * 如果bean是直接由 @Bean + new Xxx() 方法声明时，它不会被触发
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
        if (Objects.equals(beanName, "basicService")) {
            System.out.println("[MySmartInstantiationAwareBeanPostProcessor#determineCandidateConstructors] 构造方法执行前，实力化后，返回构造方法: " + beanName);
            try {
                // 有多个构造方法时，指定一个用于实例化bean的构造方法 / 当注释掉这个之后，使用默认的构造方法进行实例化，发现DemoService2为null
                Constructor<?> con = beanClass.getConstructor(DemoService2.class);
                return new Constructor<?>[]{con};
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return SmartInstantiationAwareBeanPostProcessor.super.determineCandidateConstructors(beanClass, beanName);
    }

    /**
     * 在postProcessAfterInstantiation 实力化之后，初始化之前
     * <p>
     * 当有循环依赖的场景，三级缓存策略中，借助它来直接返回实例化的对象（注意此时这个bean可能并没有初始化，没有完成注入之类的操作）
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName, "demoService") || Objects.equals(beanName, "basicService"))
            System.out.println("[MySmartInstantiationAwareBeanPostProcessor#getEarlyBeanReference] 解决循环依赖，返回实例化对象: " + beanName);
        return SmartInstantiationAwareBeanPostProcessor.super.getEarlyBeanReference(bean, beanName);
    }
}
