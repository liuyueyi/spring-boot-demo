package com.git.hui.boot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by @author yihui in 15:41 19/12/23.
 */
@Configuration
@EnableWebSecurity
public class SecurityAdapterConfig extends WebSecurityConfigurerAdapter {
    /**
     * 指定首页所有人可访问；其他url需要
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/index").permitAll().anyRequest()
                .authenticated().and().formLogin().and().httpBasic();
    }

}
