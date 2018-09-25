package com.git.hui.cloud.config.server.modal.res;

import lombok.Data;

import java.util.List;


/**
 * Created by @author yihui in 11:53 18/9/5.
 */
@Data
public class DetailResVO {
    /**
     * properties_file_id
     */
    private Long groupId;

    private String application;

    private String profile;

    private String label;

    private List<ConfVO> confList;
}
