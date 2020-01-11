package com.git.hui.boot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * - prePostEnabled :决定Spring Security的前注解是否可用 [@PreAuthorize,@PostAuthorize,..]
 * - secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用。
 * - jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed..] 是否可用。
 *
 * Created by @author yihui in 10:59 19/12/24.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
