package com.git.hui.boot.spi.demo.print;

import com.git.hui.boot.spi.egine.ISpi;

/**
 * Created by @author yihui in 16:32 18/10/23.
 */
public interface IPrint extends ISpi<Integer> {

    default void execute(Integer level, Object... msg) {
        print(msg.length > 0 ? (String) msg[0] : null);
    }

    void print(String msg);
}
