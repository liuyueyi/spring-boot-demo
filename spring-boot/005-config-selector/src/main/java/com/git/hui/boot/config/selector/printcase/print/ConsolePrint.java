package com.git.hui.boot.config.selector.printcase.print;

/**
 * Created by @author yihui in 18:54 19/12/13.
 */
public class ConsolePrint implements IPrint {
    @Override
    public void print() {
        System.out.println("控制台输出");
    }
}
