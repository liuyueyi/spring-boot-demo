package com.git.hui.boot.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by @author yihui in 15:28 19/9/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoRsp {
    private int code;
    private String msg;
    private String data;
}
