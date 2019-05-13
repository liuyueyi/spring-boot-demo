package com.git.hui.boot.redis.site.model;

import lombok.Data;

/**
 * Created by @author yihui in 16:29 19/5/12.
 */
@Data
public class VisitReqDTO {
    /**
     * 应用区分
     */
    private String app;

    /**
     * 访问者ip
     */
    private String ip;

    /**
     * 访问的URI
     */
    private String uri;
}
