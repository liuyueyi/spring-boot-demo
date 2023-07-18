package com.git.hui.boot.db;

import com.git.hui.boot.db.entity.MoneyPo;
import com.git.hui.boot.db.mapper.MoneyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * @author YiHui
 * @date 2023/7/18
 */
@Slf4j
@SpringBootApplication
public class Application {
    public Application(MoneyMapper mapper, JdbcTemplate jdbcTemplate) {
        MoneyPo po = new MoneyPo();
        po.setName("一灰");
        po.setMoney(10L);
        po.setIsDeleted(0);
        mapper.save(po);

        MoneyPo db = mapper.getById(po.getId());
        log.info("查询结果：{}", db);

        Map map = jdbcTemplate.queryForMap("select * from money where id = ?", po.getId());
        log.info("查询: {}", map);

        mapper.delete(po.getId());
        log.info("删除完成: {}", po);
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
