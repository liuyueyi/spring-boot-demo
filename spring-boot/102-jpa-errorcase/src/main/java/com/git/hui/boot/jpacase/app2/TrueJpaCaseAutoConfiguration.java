package com.git.hui.boot.jpacase.app2;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by @author yihui in 19:27 19/12/18.
 */
@Configuration
@EnableJpaRepositories("com.git.hui.boot.jpacase")
@EntityScan("com.git.hui.boot.jpacase.entity")
public class TrueJpaCaseAutoConfiguration {
}
