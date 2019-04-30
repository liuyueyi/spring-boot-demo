package com.git.hui.boot.influx.insert;

import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by @author yihui in 16:53 19/4/30.
 */
@Service
public class InsertService {

    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    public static BigDecimal formatDecimal(double num) {
        return new BigDecimal(num).setScale(8, RoundingMode.CEILING);
    }

    public void add() {
        // 以行情的kline为实例
        Point point = Point.measurement("kline_1_day").tag("id", "1").tag("date", "2019-01-10 08:00:00")
                .addField("open", formatDecimal(1.213)).addField("close", formatDecimal(1.32))
                .addField("high", formatDecimal(1.52143132424)).addField("low", formatDecimal(1.000123))
                .addField("amount", formatDecimal(13409834.1341234))
                .addField("volume", formatDecimal(123897489131234.3214)).build();
        influxDBTemplate.write(point);

        Query query = new Query("select * from kline_1_day where id='1' and date='2019-01-10 08:00:00'", "hhui");
        QueryResult result = influxDBTemplate.query(query);
        System.out.println(result);
    }


    public void batchAdd() {
        // 以行情的kline为实例
        Point point = Point.measurement("kline_1_day").tag("id", "2").tag("date", "2019-01-10 08:00:00")
                .addField("open", formatDecimal(1.213)).addField("close", formatDecimal(1.32))
                .addField("high", formatDecimal(1.52143132424)).addField("low", formatDecimal(1.000123))
                .addField("amount", formatDecimal(13409834.1341234))
                .addField("volume", formatDecimal(123897489131234.3214)).build();

        Point point2 = Point.measurement("kline_1_day").tag("id", "3").tag("date", "2019-01-10 08:00:00")
                .addField("open", formatDecimal(1.213)).addField("close", formatDecimal(1.32))
                .addField("high", formatDecimal(1.52143132424)).addField("low", formatDecimal(1.000123))
                .addField("amount", formatDecimal(13409834.1341234))
                .addField("volume", formatDecimal(123897489131234.3214)).build();
        influxDBTemplate.write(Arrays.asList(point, point2));
    }

    public void time() {
        // 以行情的kline为实例
        Point point = Point.measurement("kline_1_day").tag("id", "3").time(1547078400000L, TimeUnit.MILLISECONDS)
                .addField("open", formatDecimal(1.213)).addField("close", formatDecimal(1.32))
                .addField("high", formatDecimal(1.52143132424)).addField("low", formatDecimal(1.000123))
                .addField("amount", formatDecimal(13409834.1341234))
                .addField("volume", formatDecimal(123897489131234.3214)).build();
        influxDBTemplate.write(point);

        Query query = new Query("select * from kline_1_day where id='3'", "hhui");
        QueryResult result = influxDBTemplate.query(query);
        System.out.println(result);

    }
}
