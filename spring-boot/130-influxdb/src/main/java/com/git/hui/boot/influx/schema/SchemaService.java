package com.git.hui.boot.influx.schema;

import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by @author yihui in 16:58 19/4/30.
 */
@Service
public class SchemaService {
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    public void createDatabase() {
        influxDBTemplate.createDatabase();
    }
}
