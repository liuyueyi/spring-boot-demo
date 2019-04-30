package com.git.hui.boot.influx.config;

import org.influxdb.dto.Point;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.influxdb.DefaultInfluxDBTemplate;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBProperties;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.data.influxdb.converter.PointConverter;

/**
 * Created by @author yihui in 16:51 19/4/30.
 */
@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxAutoConfiguration {
    @Bean
    public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties) {
        return new InfluxDBConnectionFactory(properties);
    }

    @Bean
    public InfluxDBTemplate<Point> influxDBTemplate(final InfluxDBConnectionFactory connectionFactory) {
        /*
         * You can use your own 'PointCollectionConverter' implementation, e.g. in case
         * you want to use your own custom measurement object.
         */
        return new InfluxDBTemplate<>(connectionFactory, new PointConverter());
    }

    @Bean
    public DefaultInfluxDBTemplate defaultTemplate(final InfluxDBConnectionFactory connectionFactory) {
        /*
         * If you are just dealing with Point objects from 'influxdb-java' you could
         * also use an instance of class DefaultInfluxDBTemplate.
         */
        return new DefaultInfluxDBTemplate(connectionFactory);
    }
}
