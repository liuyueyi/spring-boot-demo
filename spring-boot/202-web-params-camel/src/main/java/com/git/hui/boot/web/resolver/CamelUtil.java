package com.git.hui.boot.web.resolver;

/**
 * @author yihui
 * @date 2021/8/17
 */
public class CamelUtil {
    private static final char UNDER_LINE = '_';

    /**
     * 下划线转驼峰
     *
     * @param name
     * @return
     */
    public static String toCamelCase(String name) {
        if (null == name || name.length() == 0) {
            return null;
        }

        int length = name.length();
        StringBuilder sb = new StringBuilder(length);
        boolean underLineNextChar = false;

        for (int i = 0; i < length; ++i) {
            char c = name.charAt(i);
            if (c == UNDER_LINE) {
                underLineNextChar = true;
            } else if (underLineNextChar) {
                sb.append(Character.toUpperCase(c));
                underLineNextChar = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String toUnderCase(String name) {
        if (name == null) {
            return null;
        }

        int len = name.length();
        StringBuilder res = new StringBuilder(len + 2);
        char pre = 0;
        for (int i = 0; i < len; i++) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (pre != UNDER_LINE) {
                    res.append(UNDER_LINE);
                }
                res.append(Character.toLowerCase(ch));
            } else {
                res.append(ch);
            }
            pre = ch;
        }
        return res.toString();
    }
}
