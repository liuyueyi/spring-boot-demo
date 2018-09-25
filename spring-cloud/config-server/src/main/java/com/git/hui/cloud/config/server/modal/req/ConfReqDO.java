package com.git.hui.cloud.config.server.modal.req;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 14:21 18/9/5.
 */
@Data
public class ConfReqDO implements Serializable {
    private static final long serialVersionUID = -3244492604621107296L;

    /**
     * 分组ID
     */
    private Long groupId;

    /**
     * 配置ID
     */
    private Long confId;

    /**
     * 配置Key
     */
    private String key;

    /**
     * 配置value
     */
    private String value;

}
