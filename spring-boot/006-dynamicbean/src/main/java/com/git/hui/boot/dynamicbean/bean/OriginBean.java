package com.git.hui.boot.dynamicbean.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 由Spring容器扫描的Bean，要求它被注入到我们动态注册的Bean中
 *
 * Created by @author yihui in 11:53 18/10/13.
 */
@Slf4j
@Component
public class OriginBean {

    private LocalDateTime time;

    public OriginBean() {
        time = LocalDateTime.now();
    }

    public String print(String msg) {
        return "[OriginBean] print msg: " + msg + ", time: " + time;
    }
}
