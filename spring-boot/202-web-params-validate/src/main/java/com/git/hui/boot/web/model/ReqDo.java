package com.git.hui.boot.web.model;

import com.git.hui.boot.web.ex.IdCard;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created by @author yihui in 11:37 20/5/25.
 */
@Data
public class ReqDo {

    @NotNull
    @Max(value = 100, message = "age不能超过100")
    @Min(value = 18, message = "age不能小于18")
    private Integer age;

    @NotBlank(message = "name不能为空")
    private String name;

    @NotBlank
    @Email(message = "email非法")
    private String email;

    @IdCard
    private String idCard;
}
