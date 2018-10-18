package com.git.hui.boot.conditionbean.example.conditional.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by @author yihui in 22:07 18/10/17.
 */
public class RandIntCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String type = conditionContext.getEnvironment().getProperty("conditional.rand.type");

        return "int".equalsIgnoreCase(type);
    }
}
