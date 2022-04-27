package com.git.hui.boot.influx.client;

import com.git.hui.boot.influx.client.converter.InfluxFPOConverter;
import com.git.hui.boot.influx.client.modal.InfluxFPO;

/**
 * Created by @author yihui in 10:12 19/7/25.
 */
public class FpoInfluxDBTemplate extends InfluxDBTemplate<InfluxFPO> {
    public FpoInfluxDBTemplate() {
    }

    public FpoInfluxDBTemplate(InfluxDBConnectionFactory connectionFactory, InfluxFPOConverter converter) {
        super(connectionFactory, converter);
    }
}
