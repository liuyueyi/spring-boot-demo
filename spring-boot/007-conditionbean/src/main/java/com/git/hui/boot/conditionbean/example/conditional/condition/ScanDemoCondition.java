package com.git.hui.boot.conditionbean.example.conditional.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by @author yihui in 14:34 18/10/18.
 */
public class ScanDemoCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return "true".equalsIgnoreCase(conditionContext.getEnvironment().getProperty("conditional.demo.load"));
    }
}
