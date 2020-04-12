package com.git.hui.boot.scheduler.ano;

import java.lang.annotation.*;

/**
 * Created by @author yihui in 20:26 20/4/10.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributeTask {

    /**
     * lock key
     *
     * @return
     */
    String key();
}
