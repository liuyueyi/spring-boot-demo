package com.git.hui.boot.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yihui
 * @date 2022/4/20
 */
@Controller
@EnableConfigurationProperties({DalConfig.class})
@SpringBootApplication
public class Application {
    private DalConfig dalConfig;

    public Application(DalConfig dalConfig, Environment environment) {
        this.dalConfig = dalConfig;
        System.out.println(dalConfig);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }


    @GetMapping(path = {"", "/", "/index"})
    public ModelAndView index() {
        Map<String, Object> data = new HashMap<>(2);
        data.put("info", dalConfig);
        data.put("now", LocalDateTime.now().toString());
        return new ModelAndView("index", data);
    }

}
