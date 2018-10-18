package com.git.hui.boot.conditionbean.example.conditional;

import java.util.function.Supplier;

/**
 * Created by @author yihui in 22:03 18/10/17.
 */
public class RandDataComponent<T> {

    private Supplier<T> rand;

    public RandDataComponent(Supplier<T> rand) {
        this.rand = rand;
    }

    public T rand() {
        return rand.get();
    }

}
