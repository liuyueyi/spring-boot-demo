package com.git.hui.boot.web.resolver;

/**
 * @author yihui
 * @data 2022/1/15
 */
public enum CamelType {
    /**
     * 实际传参为驼峰，需要接收的为下划线
     */
    CAMEL_TO_UNDERLINE,
    /**
     * 传参的为下划线，实际绑定时，希望传入的时驼峰
     */
    UNDERLINE_TO_CAMEL,
    /**
     * 自动适配，支持：传参驼峰、绑定下划线 + 传参下划线、绑定驼峰
     */
    AUTO;
}
