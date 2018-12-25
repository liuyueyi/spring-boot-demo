package com.git.hui.boot.redis.rank.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by @author yihui in 19:52 18/12/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankDO implements Serializable {
    private static final long serialVersionUID = 4804922606006935590L;

    /**
     * 排名
     */
    private Long rank;

    /**
     * 积分
     */
    private Float score;

    /**
     * 用户id
     */
    private Long userId;
}
