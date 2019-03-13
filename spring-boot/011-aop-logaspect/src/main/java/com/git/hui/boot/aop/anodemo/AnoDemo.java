package com.git.hui.boot.aop.anodemo;

import com.git.hui.boot.aop.logaspect.AnoDot;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by @author yihui in 19:35 19/3/13.
 */
@Component
public class AnoDemo {

    @AnoDot
    public String gen(String ans) {
        return UUID.randomUUID() + "<>" + ans;
    }
}
