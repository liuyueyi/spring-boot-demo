package com.git.hui.boot.beanutil.copier.util;

/**
 * @author yihui
 * @date 2021/4/8
 */
public class StrUtil {
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

        if (!contains(name, UNDER_LINE)) {
            return name;
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

    /**
     * 驼峰转下划线
     *
     * @param name
     * @return
     */
    public static String toUnderCase(String name) {
        if (name == null) {
            return name;
        }

        int len = name.length();
        StringBuilder res = new StringBuilder(len + 2);
        for (int i = 0; i < len; i++) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch)) {
                res.append(UNDER_LINE).append(Character.toLowerCase(ch));
            } else {
                res.append(ch);
            }
        }
        return res.toString();
    }

    public static boolean contains(String str, char searchChar) {
        return str.indexOf(searchChar) >= 0;
    }


    public static void main(String[] args) {
        String name = "userName";
        System.out.println(toCamelCase(name) + "|" + toUnderCase(name));
        name = "user_nName_User";
        System.out.println(toCamelCase(name) + "|" + toUnderCase(name));
    }
}