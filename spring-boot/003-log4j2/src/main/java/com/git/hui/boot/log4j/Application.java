package com.git.hui.boot.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 17:03 19/8/29.
 */
@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application() {
        logger.debug("debug log!!1");
        logger.info("info log!!1");
        logger.warn("warn log!!1");
        logger.error("conf log!!1");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
