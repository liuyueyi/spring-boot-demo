package com.git.hui.boot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by @author yihui in 14:54 19/12/23.
 */
@Configuration
public class SecurityAutoConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 基于内存的认证方式
     *
     * @param passwordEncoder
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder().passwordEncoder(passwordEncoder::encode);
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("1hui").password("123456").roles("guest").build());
        manager.createUser(users.username("2hui").password("666666").roles("manager").build());
        manager.createUser(users.username("3hui").password("root").roles("admin").build());
        return manager;
    }
}
