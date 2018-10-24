package com.git.hui.boot.spi.egine;

/**
 * Created by @author yihui in 16:11 18/10/23.
 */
public interface ISpi<T> {
    boolean verify(T condition);

    /**
     * 排序，数字越小，优先级越高
     * @return
     */
    default int order() {
        return 10;
    }
}
