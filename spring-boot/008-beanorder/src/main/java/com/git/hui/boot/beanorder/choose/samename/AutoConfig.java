package com.git.hui.boot.beanorder.choose.samename;

import com.git.hui.boot.beanorder.choose.samename.a.SameA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 22:14 18/10/22.
 */
@Configuration
public class AutoConfig {

    @Bean
    public SameA mySameA() {
        return new SameA();
    }

}
