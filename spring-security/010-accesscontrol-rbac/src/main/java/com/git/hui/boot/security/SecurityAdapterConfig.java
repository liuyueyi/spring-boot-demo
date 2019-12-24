package com.git.hui.boot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by @author yihui in 10:49 19/12/24.
 */
@Configuration
@EnableWebSecurity
public class SecurityAdapterConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder builder = User.builder().passwordEncoder(passwordEncoder()::encode);
        auth.inMemoryAuthentication().withUser(builder.username("hui1").password("123456").roles("guest").build());
        auth.inMemoryAuthentication().withUser(builder.username("hui2").password("123456").roles("manager").build());
        auth.inMemoryAuthentication().withUser(builder.username("hui3").password("123456").roles("admin").build());
    }

    /**
     * 指定首页所有人可访问；其他url需要
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/rbac/guest/*").hasAnyRole("guest", "manager", "admin")
                .antMatchers("/rbac/manager/*").hasAnyRole("manager", "admin")
                .antMatchers("/rbac/admin/*").hasAnyRole("admin")
                .anyRequest()
                .authenticated().and().formLogin().and().httpBasic();
    }

}
