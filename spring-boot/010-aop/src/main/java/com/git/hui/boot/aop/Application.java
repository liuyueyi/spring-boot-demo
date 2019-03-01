package com.git.hui.boot.aop;

import com.git.hui.boot.aop.demo.DemoBean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.util.Map;

/**
 * Created by @author yihui in 12:02 19/2/28.
 */
@SpringBootApplication
public class Application {

    public Application(DemoBean demoBean) {
        String ans = demoBean.randUUID(System.currentTimeMillis());
        System.out.println("----- ans: " + ans + "---------");
    }
//
    //    @SuppressWarnings("unchecked")
    //    public Application(Environment environment) {
    //        final String KEY = "alarm.title";
    //        System.out.println("old title ---> " + environment.getProperty(KEY));
    //
    //
    //        OriginTrackedValue value = (OriginTrackedValue) ((Map) ((StandardEnvironment) environment).getPropertySources()
    //                .get("applicationConfig: [classpath:/application.yml]").getSource()).get(KEY);
    //        OriginTrackedValue newVal = OriginTrackedValue.of("newTitle", value.getOrigin());
    //
    //        ((Map) ((StandardEnvironment) environment).getPropertySources()
    //                .get("applicationConfig: [classpath:/application.yml]").getSource()).put(KEY, newVal);
    //
    //        System.out.println("new title ----> " + environment.getProperty(KEY));
    //    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
