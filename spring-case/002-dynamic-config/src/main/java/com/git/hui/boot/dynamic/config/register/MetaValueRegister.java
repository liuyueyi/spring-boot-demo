package com.git.hui.boot.dynamic.config.register;

import com.git.hui.boot.dynamic.config.anno.MetaVal;
import com.git.hui.boot.dynamic.config.container.MetaContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.lang.reflect.Field;

/**
 * Created by @author yihui in 21:31 20/4/21.
 */
public class MetaValueRegister extends InstantiationAwareBeanPostProcessorAdapter {

    private MetaContainer metaContainer;

    public MetaValueRegister(MetaContainer metaContainer) {
        this.metaContainer = metaContainer;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        processMetaValue(bean);
        return super.postProcessAfterInstantiation(bean, beanName);
    }

    /**
     * 扫描bean的所有属性，并获取@MetaVal修饰的属性
     * @param bean
     */
    private void processMetaValue(Object bean) {
        try {
            Class clz = bean.getClass();
            MetaVal metaVal;
            for (Field field : clz.getDeclaredFields()) {
                metaVal = field.getAnnotation(MetaVal.class);
                if (metaVal != null) {
                    // 缓存配置与Field的绑定关系，并初始化
                    metaContainer.addInvokeCell(metaVal, bean, field);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
