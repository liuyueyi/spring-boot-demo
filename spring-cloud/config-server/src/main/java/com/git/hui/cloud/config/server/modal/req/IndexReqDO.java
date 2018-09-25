package com.git.hui.cloud.config.server.modal.req;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 11:39 18/9/5.
 */
@Data
public class IndexReqDO implements Serializable {
    private String application;
    private String profile;
    private String label;
}
