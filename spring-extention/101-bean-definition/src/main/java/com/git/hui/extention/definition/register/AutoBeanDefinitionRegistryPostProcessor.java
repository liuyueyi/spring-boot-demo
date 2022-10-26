package com.git.hui.extention.definition.register;

import com.git.hui.extention.definition.normal.NormalBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 演示自对应的bean定义
 *
 * @author YiHui
 * @date 2022/10/26
 */
@Configuration
public class AutoBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("========> postProcessBeanDefinitionRegistry --->");
        // 这个接口主要是在读取项目中的 beanDefinition 之后执行，简单来说就是项目本身的bean定义读取完毕之后，如果我们还想补充一些自定义的bean注册信息，则可以用它
        // 注意两个核心点： Spring上下文的注册Bean定义的逻辑都跑完后，但是所有的Bean都还没真正实例化之前

        // 构建bean的定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DemoBean.class, () -> {
            // 这个方法可以定义这个bean的实例创建方式，如构造函数之后还想调用其他的方法，也可以在这里做
            DemoBean demoBean = new DemoBean();
            return demoBean;
        });
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        //注册bean定义
        beanDefinitionRegistry.registerBeanDefinition("demoBean", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("========> postProcessBeanFactory --->");
        // 这个方法调用时再上面的方法执行之后，如加载自定义的bean注册依赖有其他的bean对象时，可以执行这个方法

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DemoBeanWrapper.class);
//        用下面这种方式指定构造方法的传参也可以
        builder.addConstructorArgValue(configurableListableBeanFactory.getBean("demoBean", DemoBean.class));
        BeanDefinition beanDefinition = builder.getRawBeanDefinition();
        ((DefaultListableBeanFactory) configurableListableBeanFactory).registerBeanDefinition("demoBeanWrapper", beanDefinition);


        // 针对已有的bean定义进行调整
        for (String beanName : configurableListableBeanFactory.getBeanDefinitionNames()) {
            BeanDefinition definition = configurableListableBeanFactory.getBeanDefinition(beanName);
            if (definition.getBeanClassName() == null) {
                continue;
            }

            if (definition.getBeanClassName().equalsIgnoreCase(NormalBean.class.getName())) {
                // 手动指定一下初始化方法
                definition.setInitMethodName("init");
            }
        }
    }
}
