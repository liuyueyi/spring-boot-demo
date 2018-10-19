package com.git.hui.boot.conditionbean.example.conditional;

import com.git.hui.boot.conditionbean.example.conditional.condition.RandBooleanCondition;
import com.git.hui.boot.conditionbean.example.conditional.condition.RandIntCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * Created by @author yihui in 22:05 18/10/17.
 */
@Configuration
public class ConditionalAutoConfig {

    @Bean
    @Conditional(RandIntCondition.class)
    public RandDataComponent<Integer> randIntComponent() {
        return new RandDataComponent<>(() -> {
            Random random = new Random();
            return random.nextInt(1024);
        });
    }

    @Bean
    @Conditional(RandBooleanCondition.class)
    public RandDataComponent<Boolean> randBooleanComponent() {
        return new RandDataComponent<>(() -> {
            Random random = new Random();
            return random.nextBoolean();
        });
    }
}
