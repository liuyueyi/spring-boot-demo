package com.git.hui.boot.autoconfig;

/**
 * Created by @author yihui in 09:08 18/10/29.
 */
public class BeanWrapper {

    private static AutoConfBean autoConfBean;

    public static void init(AutoConfBean autoConfBean) {
        BeanWrapper.autoConfBean = autoConfBean;
    }

    public static String getName() {
        return autoConfBean.getName();
    }

}
