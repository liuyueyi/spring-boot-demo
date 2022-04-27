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

import org.influxdb.InfluxDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class InfluxDBAccessor implements InitializingBean {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private InfluxDBConnectionFactory connectionFactory;

    /**
     * Returns the connection factory.
     *
     * @return Returns the connection factory
     */
    public InfluxDBConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Sets the connection factory.
     *
     * @param connectionFactory The connection factory to set
     */
    public void setConnectionFactory(final InfluxDBConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getDatabase() {
        return getConnectionFactory().getProperties().getDatabase();
    }

    public String getRetentionPolicy() {
        return getConnectionFactory().getProperties().getRetentionPolicy();
    }

    public InfluxDB getConnection() {
        return getConnectionFactory().getConnection();
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(getConnectionFactory(), "InfluxDBConnectionFactory is required");
    }
}
