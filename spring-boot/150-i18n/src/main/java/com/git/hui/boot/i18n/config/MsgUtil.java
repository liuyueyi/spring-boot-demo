package com.git.hui.boot.i18n.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author yihui
 * @date 2021/4/30
 */
public class MsgUtil {
    private static MessageSource messageSource;

    public static void inti(MessageSource messageSource) {
        MsgUtil.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }
}
