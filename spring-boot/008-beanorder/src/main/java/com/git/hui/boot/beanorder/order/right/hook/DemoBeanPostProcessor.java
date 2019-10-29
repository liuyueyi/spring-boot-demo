package com.git.hui.boot.beanorder.order.right.hook;

import com.git.hui.boot.order.addition2.bean.FDemo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 17:56 19/10/29.
 */
@Component
public class DemoBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalArgumentException(
                    "AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    @Nullable
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // 在bean实例化之前做某些操作
        if ("HDemo1".equals(beanName)) {
            HDemo2 demo2 = beanFactory.getBean(HDemo2.class);
            demo2.show();
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if ("application".equals(beanName)) {
            beanFactory.getBean(FDemo.class);
        }

        return true;
    }
}
