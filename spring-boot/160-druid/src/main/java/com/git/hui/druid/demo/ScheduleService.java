package com.git.hui.druid.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author YiHui
 * @date 2022/10/9
 */
@Service
public class ScheduleService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private int i = 0;

    @Transactional
    public void queryAndPrint() {
        try {
            ++i;
            List ans = jdbcTemplate.queryForList("select 1");
            System.out.println(LocalDateTime.now() + " : " + ans);
            if (i % 2 == 1) {
                ans = jdbcTemplate.queryForList("select sleep(1);"); // 慢查询，响应超时，主要针对的是 socketTimeOut 这个配置
                System.out.println(LocalDateTime.now() + " : " + ans);
            } else {
                System.out.println(LocalDateTime.now() + " : 忽略慢查询");
            }
        } catch (Exception e) {
            System.out.println(LocalDateTime.now() + " : " + e.getMessage().replaceAll("\n", "-->"));
        }
    }


}
