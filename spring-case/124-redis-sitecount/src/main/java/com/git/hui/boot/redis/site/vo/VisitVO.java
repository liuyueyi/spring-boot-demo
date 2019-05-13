package com.git.hui.boot.redis.site.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by @author yihui in 16:19 19/5/12.
 */
@Data
@AllArgsConstructor
public class VisitVO implements Serializable {
    /**
     * pv，与传统的有点区别，这里表示这个url的总访问次数；每个ip，一天次数只+1
     */
    private Long pv;

    /**
     * uv 页面总的ip访问数
     */
    private Long uv;

    /**
     * 当前ip，第一次访问本url的排名
     */
    private Long rank;

    /**
     * 热度，每次访问计数都+1
     */
    private Long hot;

    public VisitVO() {
    }

    public VisitVO(VisitVO visitVO) {
        this.pv = visitVO.pv;
        this.uv = visitVO.uv;
        this.rank = visitVO.rank;
        this.hot = visitVO.hot;
    }
}
