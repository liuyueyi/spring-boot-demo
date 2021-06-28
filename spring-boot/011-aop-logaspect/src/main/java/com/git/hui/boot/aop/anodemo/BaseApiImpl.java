package com.git.hui.boot.aop.anodemo;

import com.git.hui.boot.aop.logaspect.AnoDot;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 2021/5/24
 */
@Component
public class BaseApiImpl implements BaseApi {
    @Override
    public String print(String obj) {
        System.out.println("ano in interface:" + obj);
        return "return:" + obj;
    }

    @AnoDot
    @Override
    public String print2(String obj) {
        System.out.println("ano in impl:" + obj);
        return "return:" + obj;
    }
}
