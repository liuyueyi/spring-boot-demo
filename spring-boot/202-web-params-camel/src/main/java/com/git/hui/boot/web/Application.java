package com.git.hui.boot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.git.hui.boot.web.resolver.CamelArgumentResolver;
import com.git.hui.boot.web.resolver.databinder.ParamArgumentProcessor;
import com.git.hui.boot.web.resolver.databinder.ParamAttrProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Created by @author yihui in 11:36 20/5/25.
 */
@SpringBootApplication
public class Application  extends WebMvcConfigurationSupport {
//
//    @Bean
//    public ParamArgumentProcessor argumentProcessor() {
//        return new ParamArgumentProcessor(true);
//    }


    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CamelArgumentResolver());
//        argumentResolvers.add(new SimpleArgumentProcessor(true));
        argumentResolvers.add(new ParamAttrProcessor());
        argumentResolvers.add(new ParamArgumentProcessor());
    }


    /**
     * 下面这个设置，可以实现json参数解析/返回时，传入的下划线转驼峰；输出的驼峰转下划线
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();

        // 设置驼峰标志转下划线
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        // 设置格式化内容
        converter.setObjectMapper(objectMapper);
        converters.add(0, converter);
        super.extendMessageConverters(converters);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
