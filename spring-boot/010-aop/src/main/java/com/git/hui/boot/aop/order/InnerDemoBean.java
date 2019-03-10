package com.git.hui.boot.aop.order;

import com.git.hui.boot.aop.annotation.AnoDot;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 用于测试不同类型的advice，对同一个方法拦截的先后顺序
 * Created by @author yihui in 19:35 19/3/9.
 */
@Component
public class InnerDemoBean {

    @AnoDot
    public String print() {
        try {
            System.out.println("in innerDemoBean start!");
            String rans = System.currentTimeMillis() + "|" + UUID.randomUUID();
            System.out.println(rans);
            return rans;
        } finally {
            System.out.println("in innerDemoBean over!");
        }
    }

}
