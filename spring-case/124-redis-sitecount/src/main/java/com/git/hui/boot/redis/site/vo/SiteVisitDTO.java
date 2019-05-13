package com.git.hui.boot.redis.site.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by @author yihui in 17:15 19/5/12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteVisitDTO {

    /**
     * 站点访问统计
     */
    private VisitVO siteVO;

    /**
     * 页面访问统计
     */
    private VisitVO uriVO;

}
