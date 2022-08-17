package com.git.hui.web.res;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author YiHui
 * @date 2022/8/17
 */
@SpringBootApplication
public class Application implements WebMvcConfigurer {
//  fixme  如果发现xml返回异常，可以将下面的注释打开
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new MappingJackson2XmlHttpMessageConverter());
//    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(true)
                // 禁用accept协商方式，即不关心前端传的accept值
//                .ignoreAcceptHeader(true)
                // 哪个放在前面，哪个的优先级就高； 当上面这个accept未禁用时，若请求传的accept不能覆盖下面两种，则会出现406错误
                .defaultContentType(MediaType.APPLICATION_JSON)
                // 根据传参mediaType来决定返回样式
                .parameterName("mediaType")
                // 当acceptHeader未禁用时，accept的值与mediaType传参的值不一致时，以mediaType的传值为准
                // mediaType值可以不传，为空也行，但是不能是json/xml之外的其他值
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
