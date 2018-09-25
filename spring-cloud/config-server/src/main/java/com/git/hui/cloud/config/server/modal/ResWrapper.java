package com.git.hui.cloud.config.server.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by @author yihui in 11:19 18/9/5.
 */
@JsonInclude
@Data
public class ResWrapper<T> {
    private int code;
    private String msg;
    private T data;

    public ResWrapper() {
    }

    public ResWrapper(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResWrapper<T> buildSuccess(T data) {
        return new ResWrapper<>(200, "success", data);
    }

    public static <T> ResWrapper<T> buildError(int code, String msg, T data) {
        return new ResWrapper<>(code, msg, data);
    }
}
