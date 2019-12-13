package com.git.hui.boot.importbean.bean;

import com.git.hui.boot.importbean.anno.Meta;

/**
 * Created by @author yihui in 12:08 19/12/13.
 */
@Meta
public class DependBean {
    public DependBean(NormalBean normalBean) {
        System.out.println("depend bean! " + normalBean);
    }
}
