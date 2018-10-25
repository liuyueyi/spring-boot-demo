package com.git.hui.boot.spi.exception;

/**
 * Created by @author yihui in 16:24 18/10/23.
 */
public class NoSpiChooseException extends RuntimeException {

    public NoSpiChooseException(String text) {
        super(text);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
