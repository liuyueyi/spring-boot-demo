package com.git.hui.demo.report;

import com.git.hui.demo.report.service.StatisticAndReportService;
import com.git.hui.demo.report.service.StatisticVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yihui
 * @date 22/8/16
 */
@Controller
public class StatisticReportRest {

    @Autowired
    private StatisticAndReportService statisticAndReportSchedule;

    @GetMapping(path = "report")
    public String view(Model model) {
        StatisticVo vo = statisticAndReportSchedule.statisticAddUserReport();
        model.addAttribute("vo", vo);
        return "report";
    }

}
