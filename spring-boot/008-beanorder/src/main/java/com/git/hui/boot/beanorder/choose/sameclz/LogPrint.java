package com.git.hui.boot.beanorder.choose.sameclz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 18:23 18/10/22.
 */
@Slf4j
@Component
@Primary
public class LogPrint implements IPrint {
    @Override
    public void print(String msg) {
        log.info("log print: {}", msg);
    }
}
