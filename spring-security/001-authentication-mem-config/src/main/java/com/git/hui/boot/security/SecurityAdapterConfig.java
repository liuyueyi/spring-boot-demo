package com.git.hui.boot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by @author yihui in 10:49 19/12/24.
 */
@Configuration
public class SecurityAdapterConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 测试时，可以直接用下面的方式
        //        User.UserBuilder builder = User.withDefaultPasswordEncoder();
        User.UserBuilder builder = User.builder().passwordEncoder(passwordEncoder()::encode);
        auth.inMemoryAuthentication().withUser(builder.username("hui1").password("123456").roles("guest").build());
        auth.inMemoryAuthentication().withUser(builder.username("hui2").password("123456").roles("guest").build());
    }


}
