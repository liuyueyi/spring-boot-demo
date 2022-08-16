package com.git.hui.demo.report.dao;

import com.git.hui.demo.report.dao.po.UserStatisticPo;

import java.util.List;

/**
 * @author YiHui
 * @date 2022/8/16
 */
public interface UserStatisticMapper {

    /**
     * 统计最近多少天内的新增用户数
     *
     * @param days 统计的天数，从当前这一天开始
     * @return
     */
    List<UserStatisticPo> statisticUserCnt(int days);

}
