package com.git.hui.boot.web.common;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 10:39 20/4/5.
 */
@RestController
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdapterController {
}
