package com.git.hui.boot.redis;

import com.git.hui.boot.redis.site.SiteVisitFacade;
import com.git.hui.boot.redis.site.model.VisitReqDTO;
import com.git.hui.boot.redis.site.vo.SiteVisitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by @author yihui in 18:58 19/5/12.
 */
@Controller
public class VisitController {
    @Autowired
    private SiteVisitFacade siteVisitFacade;

    @RequestMapping(path = "visit")
    @ResponseBody
    public SiteVisitDTO visit(VisitReqDTO reqDTO) {
        return siteVisitFacade.visit(reqDTO);
    }
}
