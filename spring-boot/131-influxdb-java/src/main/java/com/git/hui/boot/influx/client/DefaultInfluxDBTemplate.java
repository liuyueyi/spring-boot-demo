package com.git.hui.boot.influx.client;

import com.git.hui.boot.influx.client.converter.FPOConverter;

/**
 * Created by @author yihui in 10:12 19/7/25.
 */
public class DefaultInfluxDBTemplate extends InfluxDBTemplate<Object> {
    public DefaultInfluxDBTemplate() {
    }

    public DefaultInfluxDBTemplate(InfluxDBConnectionFactory connectionFactory,
            FPOConverter<Object> converter) {
        super(connectionFactory, converter);
    }
}
