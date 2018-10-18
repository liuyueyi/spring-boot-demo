package com.git.hui.boot.conditionbean.example.depends;

import com.git.hui.boot.conditionbean.example.depends.bean.DependedBean;
import com.git.hui.boot.conditionbean.example.depends.bean.LoadIfBeanExist;
import com.git.hui.boot.conditionbean.example.depends.bean.LoadIfBeanNotExists;
import com.git.hui.boot.conditionbean.example.depends.clz.DependedClz;
import com.git.hui.boot.conditionbean.example.depends.clz.LoadIfClzExists;
import com.git.hui.boot.conditionbean.example.depends.clz.LoadIfClzNotExists;
import com.git.hui.boot.conditionbean.example.properties.PropertyExistBean;
import com.git.hui.boot.conditionbean.example.properties.PropertyNotExistBean;
import com.git.hui.boot.conditionbean.example.properties.PropertyValueExistBean;
import com.git.hui.boot.conditionbean.example.properties.PropertyValueNotExistBean;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by @author yihui in 09:15 18/10/18.
 */
@Configuration
public class ConditionDependAutoConfig {
    private Environment environment;

    public ConditionDependAutoConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DependedBean dependedBean() {
        return new DependedBean();
    }

    /**
     * 只有当DependedBean 存在时，才会创建bean: `LoadIfBeanExist`
     *
     * @return
     */
    @Bean
    @ConditionalOnBean(name = "dependedBean")
    public LoadIfBeanExist loadIfBeanExist() {
        return new LoadIfBeanExist("dependedBean");
    }

    /**
     * 只有当没有notExistsBean时，才会创建bean: `LoadIfBeanNotExists`
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "notExistsBean")
    public LoadIfBeanNotExists loadIfBeanNotExists() {
        return new LoadIfBeanNotExists("notExistsBean");
    }

    /**
     * 当引用了 {@link DependedClz} 类之后，才会创建bean： `LoadIfClzExists`
     *
     * @return
     */
    @Bean
    @ConditionalOnClass(DependedClz.class)
    public LoadIfClzExists loadIfClzExists() {
        return new LoadIfClzExists("dependedClz");
    }

    /**
     * 当系统中没有 com.git.hui.boot.conditionbean.example.depends.clz.DependedClz类时，才会创建这个bean
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingClass("com.git.hui.boot.conditionbean.example.depends.clz.DependedClz")
    public LoadIfClzNotExists loadIfClzNotExists() {
        return new LoadIfClzNotExists("com.git.hui.boot.conditionbean.example.depends.clz.DependedClz");
    }

    /**
     * 配置存在时才会加载这个bean
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty("conditional.property")
    public PropertyExistBean propertyExistBean() {
        return new PropertyExistBean(environment.getProperty("conditional.property"));
    }

    /**
     * 要求配置不存在时，才会加载这个bean
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "conditional.property.no")
    public PropertyNotExistBean propertyNotExistBean() {
        return new PropertyNotExistBean("conditional.property");
    }

    @Bean
    @ConditionalOnProperty(name = {"conditional.property"}, havingValue = "properExists")
    public PropertyValueExistBean propertyValueExistBean() {
        return new PropertyValueExistBean("properExists");
    }

    @Bean
    @ConditionalOnProperty(name = {"conditional.property"}, havingValue = "properNotExists")
    public PropertyValueNotExistBean propertyValueNotExistBean() {
        return new PropertyValueNotExistBean("properNotExists");
    }
}
