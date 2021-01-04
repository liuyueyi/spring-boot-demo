package com.git.hui.boot.multi.datasource.server;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author yihui
 * @date 20/12/27
 */
@Service
public class JdbcServer implements ApplicationContextAware {

    private JdbcTemplate storyJdbcTemplate;

    private JdbcTemplate testJdbcTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, DataSource> map = applicationContext.getBeansOfType(DataSource.class);
        System.out.println(map);
        storyJdbcTemplate = new JdbcTemplate(map.get("storyDataSource"));
        testJdbcTemplate = new JdbcTemplate(map.get("testDataSource"));
    }

    public void query() {
        List<Map<String, Object>> storyRes = storyJdbcTemplate.queryForList("select * from money where id in (1, 1000)");
        List<Map<String, Object>> testRes = testJdbcTemplate.queryForList("select * from money where id in (1, 1000)");
        System.out.println(storyRes);
        System.out.println("--------------");
        System.out.println(testRes);
    }
}
