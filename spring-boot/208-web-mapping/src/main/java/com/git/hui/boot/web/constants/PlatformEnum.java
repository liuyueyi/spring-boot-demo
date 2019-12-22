package com.git.hui.boot.web.constants;

import lombok.Getter;

/**
 * Created by @author yihui in 16:20 19/12/22.
 */
public enum PlatformEnum {
    PC("pc", 1), APP("app", 1), WAP("wap", 1), ALL("all", 0);

    @Getter
    private String name;

    @Getter
    private int order;

    PlatformEnum(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public static PlatformEnum nameOf(String name) {
        if (name == null) {
            return ALL;
        }

        name = name.toLowerCase().trim();
        for (PlatformEnum sub : values()) {
            if (sub.name.equals(name)) {
                return sub;
            }
        }

        return ALL;
    }
}
