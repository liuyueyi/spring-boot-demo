package com.git.hui.boot.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 15:57 20/4/6.
 */
@Data
public class BasePo implements Serializable {
    private static final long serialVersionUID = -1136173266983480386L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

}
