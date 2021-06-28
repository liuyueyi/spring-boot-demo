package com.git.hub.boot.web.enhanced.anno;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author yihui
 * @date 2021/3/9
 */
@RestController
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RestService {
}
