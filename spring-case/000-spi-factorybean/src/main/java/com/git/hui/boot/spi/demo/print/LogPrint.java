package com.git.hui.boot.spi.demo.print;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:41 18/10/23.
 */
@Slf4j
@Component
public class LogPrint implements IPrint {
    @Override
    public void print(String msg) {
        log.info("log print: {}", msg);
    }

    @Override
    public boolean verify(Integer condition) {
        return condition > 0;
    }
}
