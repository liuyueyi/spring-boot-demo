package com.git.hui.extention.postprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author YiHui
 * @date 2022/11/4
 */
@SpringBootApplication
@Configuration
public class Application {

// 也可以通过这种方式直接指定 需要加载的配置文件，这样就不需要再 UserService 类上添加 @PropertySource("classpath:user.properties")
//    @Bean
//    @Primary
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        DecryptPropertSourcePlaceHolderConfigurer propertSourcePlaceHolderConfigurer = new DecryptPropertSourcePlaceHolderConfigurer();
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource resource = resolver.getResource("classpath:user.properties");
//        propertSourcePlaceHolderConfigurer.setLocation(resource);
//        return propertSourcePlaceHolderConfigurer;
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
