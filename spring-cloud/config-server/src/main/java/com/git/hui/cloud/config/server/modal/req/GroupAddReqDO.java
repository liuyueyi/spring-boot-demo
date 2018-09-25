package com.git.hui.cloud.config.server.modal.req;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 12:03 18/9/5.
 */
@Data
public class GroupAddReqDO implements Serializable {
    private static final long serialVersionUID = -9182938102620813530L;
    private String application;
    private String label;
    private String profile;
    private String content;
}
