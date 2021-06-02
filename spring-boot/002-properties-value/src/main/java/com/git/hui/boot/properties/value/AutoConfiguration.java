package com.git.hui.boot.properties.value;

import com.git.hui.boot.properties.value.model.Jwt;
import com.git.hui.boot.properties.value.parse.JwtConverter;
import com.git.hui.boot.properties.value.parse.JwtEditor;
import com.git.hui.boot.properties.value.parse.JwtFormatter;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import java.util.Collections;

/**
 * @author yihui
 * @date 2021/6/2
 */
@Configuration
public class AutoConfiguration {
    /**
     * 注册自定义的 propertyEditor
     *
     * @return
     */
//    @Bean
    public CustomEditorConfigurer editorConfigurer() {
        CustomEditorConfigurer editorConfigurer = new CustomEditorConfigurer();
        editorConfigurer.setCustomEditors(Collections.singletonMap(Jwt.class, JwtEditor.class));
        return editorConfigurer;
    }

    /**
     * 注册自定义的converter
     *
     * @return
     */
    @Bean("conversionService")
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(Collections.singleton(new JwtConverter()));
        return factoryBean;
    }

//    @Bean("conversionService")
    public FormattingConversionServiceFactoryBean conversionService2() {
        FormattingConversionServiceFactoryBean factoryBean = new FormattingConversionServiceFactoryBean();
        factoryBean.setConverters(Collections.singleton(new JwtConverter()));
        factoryBean.setFormatters(Collections.singleton(new JwtFormatter()));
        return factoryBean;
    }
}
