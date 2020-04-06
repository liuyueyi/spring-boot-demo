package com.git.hui.boot.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author YiHui
 * @since 2020-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StoryT0 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作者的userID
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 作者名
     */
    private String name;

    /**
     * 密码
     */
    private String title;

    /**
     * 故事内容
     */
    private String story;

    private Boolean isDeleted;

    private String createAt;

    private String updateAt;

    private Integer tag;


}
