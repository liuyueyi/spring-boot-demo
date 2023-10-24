package com.git.hui.id.snowflake;

import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * HuTool 雪花算法：
 * 标记位 时间戳(41字节) datacenter(5字节) workerId(5字节) 自增序列号(12字节)
 *
 * @author YiHui
 * @date 2023/10/23
 */
@Component
public class HuToolSnowFlakeProducer {
    private static final Date EPOC = new Date(2023, 1, 1);

    private Snowflake snowflake;

    public HuToolSnowFlakeProducer() {
        List<Integer> list = DevUtil.calculateDefaultInfo(5, 5);
        snowflake = new Snowflake(list.get(1), list.get(0));
    }

    public HuToolSnowFlakeProducer(int workId, int dataCenter) {
        snowflake = new Snowflake(EPOC, workId, dataCenter, false);
    }

    public Long nextId() {
        return snowflake.nextId();
    }
}
