package com.git.hui.demo.report.service;

import com.git.hui.demo.report.dao.po.UserStatisticPo;
import lombok.Data;

import java.util.List;

/**
 * @author yihui
 * @date 22/8/16
 */
@Data
public class StatisticVo {

    private List<UserStatisticPo> list;

    private String htmlTitle;

    private String tableTitle;

}
