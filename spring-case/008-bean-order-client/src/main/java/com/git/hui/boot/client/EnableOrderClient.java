package com.git.hui.boot.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 11:40 20/2/28.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ClientAutoConfiguration.class, ClientBeanProcessor.class})
public @interface EnableOrderClient {
}
