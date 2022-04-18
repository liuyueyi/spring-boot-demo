/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.git.hui.boot.influx.client;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

public class InfluxDBConnectionFactory implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(InfluxDBConnectionFactory.class);

    private InfluxDB connection;

    private InfluxDBProperties properties;

    public InfluxDBConnectionFactory() {

    }

    public InfluxDBConnectionFactory(final InfluxDBProperties properties) {
        this.properties = properties;
    }

    public InfluxDB getConnection() {
        Assert.notNull(getProperties(), "InfluxDBProperties are required");
        if (connection == null) {
            final Builder client =
                    new OkHttpClient.Builder().connectTimeout(properties.getConnectTimeout(), TimeUnit.SECONDS)
                            .writeTimeout(properties.getWriteTimeout(), TimeUnit.SECONDS)
                            .readTimeout(properties.getReadTimeout(), TimeUnit.SECONDS);
            connection = InfluxDBFactory
                    .connect(properties.getUrl(), properties.getUsername(), properties.getPassword(), client);
            logger.debug("Using InfluxDB '{}' on '{}'", properties.getDatabase(), properties.getUrl());
            if (properties.isGzip()) {
                logger.debug("Enabled gzip compression for HTTP requests");
                connection.enableGzip();
            }
        }
        return connection;
    }

    /**
     * Returns the configuration properties.
     *
     * @return Returns the configuration properties
     */
    public InfluxDBProperties getProperties() {
        return properties;
    }

    /**
     * Sets the configuration properties.
     *
     * @param properties The configuration properties to set
     */
    public void setProperties(final InfluxDBProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(getProperties(), "InfluxDBProperties are required");
    }
}
