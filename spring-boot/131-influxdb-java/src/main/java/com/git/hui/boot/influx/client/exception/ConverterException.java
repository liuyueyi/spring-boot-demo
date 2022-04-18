package com.git.hui.boot.influx.client.exception;

/**
 * Created by @author yihui in 09:56 19/7/25.
 */
public class ConverterException extends RuntimeException {
    public ConverterException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
