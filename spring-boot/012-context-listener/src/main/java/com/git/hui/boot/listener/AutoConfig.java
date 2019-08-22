package com.git.hui.boot.listener;

import com.git.hui.boot.listener.context.MyContextAppHolder;
import com.git.hui.boot.listener.context.MyContextListener;
import com.git.hui.boot.listener.demo.DemoBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 08:55 19/8/21.
 */
@Configuration
public class AutoConfig {

    @Bean
    public MyContextAppHolder myContextAppHolder() {
        return new MyContextAppHolder();
    }

    @Bean
    public DemoBean demoBean(MyContextAppHolder myContextAppHolder) throws Exception {
        return myContextAppHolder.getObject();
    }

    @Bean
    public MyContextListener myContextListener() {
        return new MyContextListener();
    }

}
