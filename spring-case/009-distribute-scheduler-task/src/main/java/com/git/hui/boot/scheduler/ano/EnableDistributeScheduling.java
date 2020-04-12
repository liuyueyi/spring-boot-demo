package com.git.hui.boot.scheduler.ano;

import com.git.hui.boot.scheduler.config.MyScheduledAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 20:15 20/4/10.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MyScheduledAnnotationBeanPostProcessor.class)
@Documented
public @interface EnableDistributeScheduling {
}
