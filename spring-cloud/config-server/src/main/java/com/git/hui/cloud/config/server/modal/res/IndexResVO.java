package com.git.hui.cloud.config.server.modal.res;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 11:42 18/9/5.
 */
@Data
public class IndexResVO implements Serializable {
    private static final long serialVersionUID = 6759274823993490898L;
    private Long groupId;
    private String application;
    private String label;
    private String profile;
    private String user;
    private Long date;
}
