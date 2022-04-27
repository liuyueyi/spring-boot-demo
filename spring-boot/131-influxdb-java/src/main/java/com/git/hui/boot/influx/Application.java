package com.git.hui.boot.influx;

import com.git.hui.boot.influx.client.InfluxDBTemplate;
import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by @author yihui in 18:23 19/8/13.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private static final String DATABASE = "test";

    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    @Data
    @Measurement(database = DATABASE, name = "cpu")
    public static class CPU {
        @Column(name = "id")
        private Integer id;

        @Column(name = "time")
        private Long time;

        @Column(name = "tenant", tag = true)
        private String tenant;

        @Column(name = "idle")
        private Long idle;
        @Column(name = "user")
        private Long user;
        @Column(name = "system")
        private Long system;
        @Column(name = "price")
        private BigDecimal price;
    }

    @Override
    public void run(final String... args) throws Exception {
        // 创建数据库
        influxDBTemplate.createDatabase();
        // 提前创建两个策略,
        // create retention policy "1_h" on test duration 1h replication 1
        // create retention policy "2_h" on test duration 2h replication 1
        // Create some data...
        final Point p1 = Point.measurement("cpu").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("tenant", "default").addField("idle", 90L).addField("user", 9L).addField("system", 1L)
                .addField("price", new BigDecimal(100.24).setScale(4, RoundingMode.CEILING)).addField("id", 1).build();
        influxDBTemplate.write("yhh", "1_h", p1);

        final Point p2 =
                Point.measurement("cpu").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).tag("tenant", "dragon")
                        .addField("idle", 80L).addField("user", 19L).addField("system", 4L)
                        .addField("price", new BigDecimal(600.24).setScale(4, RoundingMode.CEILING)).addField("id", 2)
                        .build();
        influxDBTemplate.write("yhh", "2_h", p2);

        // 当策略并不是默认的时候，需要带上策略进行查询
        List<CPU> result = influxDBTemplate.queryForObject(new Query("select * from \"1_h\".cpu", influxDBTemplate.getDatabase()), CPU.class);
        System.out.println(result);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
