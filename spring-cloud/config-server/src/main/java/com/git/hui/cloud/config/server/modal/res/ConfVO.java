package com.git.hui.cloud.config.server.modal.res;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 15:41 18/9/5.
 */
@Data
public class ConfVO implements Serializable {
    /**
     * property confId
     */
    private Long confId;
    private String key;
    private String value;
    private Long create;
    private Long update;
}
