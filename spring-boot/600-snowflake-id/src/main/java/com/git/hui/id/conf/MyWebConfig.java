package com.git.hui.id.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.standard.serializer.IStandardJavaScriptSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * 注册xml解析器
 *
 * @author yihui
 * @date 2022/6/20
 */
@Slf4j
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Resource
    private TemplateEngine templateEngine;

    @PostConstruct
    public void init() {
        log.info("XmlWebConfig init...");
        // 通过templateEngine获取SpringStandardDialect
        SpringStandardDialect springStandardDialect = CollectionUtils.findValueOfType(templateEngine.getDialects(), SpringStandardDialect.class);
//         方式1. 通过自定义重写 StandardJavaScriptSerializer 方式，支持序列化的长整型转换
        springStandardDialect.setJavaScriptSerializer(new MyStandardJavaScriptSerializer(true));
        System.out.println("over");

        // 方式2. 使用反射的方式，在序列化框架上添加长整型转String
//        reflectRegistertModule(springStandardDialect);
    }

    private void reflectRegistertModule(SpringStandardDialect springStandardDialect) {
        IStandardJavaScriptSerializer standardJavaScriptSerializer = springStandardDialect.getJavaScriptSerializer();
        // 反射获取 IStandardJavaScriptSerializer
        Field delegateField = ReflectionUtils.findField(standardJavaScriptSerializer.getClass(), "delegate");
        if (delegateField == null) {
            log.warn("delegeteField is null !!!");
            return;
        }
        ReflectionUtils.makeAccessible(delegateField);
        Object delegate = ReflectionUtils.getField(delegateField, standardJavaScriptSerializer);
        if (delegate == null) {
            log.warn("delegete is null !!!");
            return;
        }
        // 如果代理类是JacksonStandardJavaScriptSerializer,则获取mapper,设置model
        if (Objects.equals("JacksonStandardJavaScriptSerializer", delegate.getClass().getSimpleName())) {
            Field mapperField = ReflectionUtils.findField(delegate.getClass(), "mapper");
            if (mapperField == null) {
                log.warn("mapperField is null !!!");
                return;
            }
            ReflectionUtils.makeAccessible(mapperField);
            ObjectMapper objectMapper = (ObjectMapper) ReflectionUtils.getField(mapperField, delegate);
            if (objectMapper == null) {
                log.warn("mapper is null !!!");
                return;
            }
            // 设置序列化Module,修改long型序列化为字符串
            objectMapper.registerModule(JacksonUtil.bigIntToStrsimpleModule());
            log.info("WebConf init 设置jackson序列化长整型为字符串成功!!!");
        }
    }

    /**
     * 配置序列化方式
     *
     * @param converters
     */
    @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter convert = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        // 长整型序列化返回时，更新为string，避免前端js精度丢失
        // 注意这个仅适用于json数据格式的返回，对于Thymeleaf的模板渲染依然会出现精度问题
        mapper.registerModule(JacksonUtil.bigIntToStrsimpleModule());
        convert.setObjectMapper(mapper);
        converters.add(0, convert);
    }
}
