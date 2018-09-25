package com.git.hui.cloud.api.eurka.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 08:07 18/9/2.
 */
@Data
public class UserDTO implements Serializable {
    private Long userId;
    private String userName;
    private Long phone;
    private String nickname;
}
