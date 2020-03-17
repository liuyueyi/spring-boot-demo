package com.git.hui.boot.client;

import com.git.hui.boot.client.load.DatasourceLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * Created by @author yihui in 17:56 19/10/29.
 */
public class ClientBeanProcessor extends InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        System.out.println("准备实例: " + beanName);

        return super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalArgumentException(
                    "AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
        }

        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        DatasourceLoader propertyLoader = this.beanFactory.getBean(DatasourceLoader.class);
        System.out.println(propertyLoader);
    }

}
