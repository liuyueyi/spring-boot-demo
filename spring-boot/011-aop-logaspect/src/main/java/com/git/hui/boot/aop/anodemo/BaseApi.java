package com.git.hui.boot.aop.anodemo;

import com.git.hui.boot.aop.logaspect.AnoDot;

/**
 * @author yihui
 * @date 2021/5/24
 */
public interface BaseApi {
    @AnoDot
    String print(String obj);

    String print2(String obj);
}
