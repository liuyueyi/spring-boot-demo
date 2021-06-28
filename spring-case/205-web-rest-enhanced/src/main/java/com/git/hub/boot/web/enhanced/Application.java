package com.git.hub.boot.web.enhanced;

import com.git.hub.boot.web.enhanced.mapper.RestAdapterHandlerMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author yihui
 * @date 2021/3/9
 */
@Configuration
@SpringBootApplication
public class Application implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RestAdapterHandlerMapping();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
