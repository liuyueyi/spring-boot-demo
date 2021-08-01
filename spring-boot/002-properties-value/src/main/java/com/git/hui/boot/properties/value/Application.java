package com.git.hui.boot.properties.value;


import com.git.hui.boot.properties.value.config.ConfigProperties;
import com.git.hui.boot.properties.value.config.SpelProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yihui
 * @date 2021/6/2
 */
@RestController
@SpringBootApplication
public class Application {
    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private SpelProperties spelProperties;

    @GetMapping("show")
    public String showProperties() {
        return configProperties.toJsonStr();
    }


    @GetMapping("spel")
    public SpelProperties showSpel() {
        return spelProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
