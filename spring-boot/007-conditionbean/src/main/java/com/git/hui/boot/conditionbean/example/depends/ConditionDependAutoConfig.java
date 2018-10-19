package com.git.hui.boot.conditionbean.example.depends;

import com.git.hui.boot.conditionbean.example.depends.bean.DependedBean;
import com.git.hui.boot.conditionbean.example.depends.bean.LoadIfBeanExist;
import com.git.hui.boot.conditionbean.example.depends.bean.LoadIfBeanNotExists;
import com.git.hui.boot.conditionbean.example.depends.clz.DependedClz;
import com.git.hui.boot.conditionbean.example.depends.clz.LoadIfClzExists;
import com.git.hui.boot.conditionbean.example.depends.clz.LoadIfClzNotExists;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 09:15 18/10/18.
 */
@Configuration
public class ConditionDependAutoConfig {
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
}
