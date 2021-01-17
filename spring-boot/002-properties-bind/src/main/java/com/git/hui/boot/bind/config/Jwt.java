package com.git.hui.boot.bind.config;

import lombok.Data;

/**
 * @author yihui
 * @date 21/1/17
 */
@Data
public class Jwt {

    private String token;

    private Long timestamp;

}
