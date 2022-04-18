package com.git.hui.boot.influx;

import com.git.hui.boot.influx.client.InfluxDBConnectionFactory;
import com.git.hui.boot.influx.client.InfluxDBProperties;
import com.git.hui.boot.influx.client.InfluxDBTemplate;
import com.git.hui.boot.influx.client.converter.PointConverter;
import org.influxdb.dto.Point;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBConfiguration {
    @Bean
    public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties) {
        return new InfluxDBConnectionFactory(properties);
    }

    @Bean
    public InfluxDBTemplate<Point> influxDBTemplate(final InfluxDBConnectionFactory connectionFactory) {
        return new InfluxDBTemplate<>(connectionFactory, new PointConverter());
    }
}
