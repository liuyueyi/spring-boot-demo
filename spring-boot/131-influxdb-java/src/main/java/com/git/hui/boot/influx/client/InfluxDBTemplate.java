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

import lombok.Getter;
import com.git.hui.boot.influx.client.converter.PointCollectionConverter;
import com.git.hui.boot.influx.client.mapper.MapInfluxDBResultMapper;
import com.git.hui.boot.influx.client.mapper.ObjectInfluxDbResultMapper;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class InfluxDBTemplate<T> extends InfluxDBAccessor implements InfluxDBOperations<T> {
    private PointCollectionConverter<T> converter;
    @Getter
    private ObjectInfluxDbResultMapper objMapper = new ObjectInfluxDbResultMapper();
    @Getter
    private MapInfluxDBResultMapper mapMapper = new MapInfluxDBResultMapper();

    public InfluxDBTemplate() {
    }

    public InfluxDBTemplate(final InfluxDBConnectionFactory connectionFactory,
            final PointCollectionConverter<T> converter) {
        setConnectionFactory(connectionFactory);
        setConverter(converter);
    }


    public void setConverter(final PointCollectionConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Assert.notNull(converter, "PointCollectionConverter is required");
    }

    @Override
    public void createDatabase() {
        final String database = getDatabase();
        getConnection().createDatabase(database);
    }

    @Override
    public void createDatabase(String database) {
        getConnection().createDatabase(database);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void write(final T... payload) {
        final String database = getDatabase();
        final String retentionPolicy = getConnectionFactory().getProperties().getRetentionPolicy();
        write(database, retentionPolicy, payload);
    }

    @Override
    public void write(String database, T... payload) {
        final String retentionPolicy = getConnectionFactory().getProperties().getRetentionPolicy();
        write(database, retentionPolicy, payload);
    }

    @Override
    public void write(String database, String retention, T... payload) {
        final BatchPoints ops =
                BatchPoints.database(database).retentionPolicy(retention).consistency(InfluxDB.ConsistencyLevel.ALL)
                        .build();
        for (T s : payload) {
            if (s instanceof Collection) {
                ((Collection) s).forEach(sub -> ops.point(Objects.requireNonNull(converter.convert((T) sub))));
            } else {
                ops.point(Objects.requireNonNull(converter.convert(s)));
            }
        }
        getConnection().write(ops);
    }

    @Override
    public void write(final List<T> payload) {
        final String database = getDatabase();
        write(database, payload);
    }

    @Override
    public void write(String database, List<T> payload) {
        final String retentionPolicy = getConnectionFactory().getProperties().getRetentionPolicy();
        write(database, retentionPolicy, payload);
    }

    @Override
    public void write(String database, String retention, List<T> payload) {
        final BatchPoints ops =
                BatchPoints.database(database).retentionPolicy(retention).consistency(InfluxDB.ConsistencyLevel.ALL)
                        .build();
        payload.forEach(t -> ops.point(Objects.requireNonNull(converter.convert(t))));
        getConnection().write(ops);
    }

    @Override
    public QueryResult query(final Query query) {
        return getConnection().query(query);
    }

    @Override
    public QueryResult query(final Query query, final TimeUnit timeUnit) {
        return getConnection().query(query, timeUnit);
    }

    @Override
    public void query(Query query, int chunkSize, Consumer<QueryResult> consumer) {
        getConnection().query(query, chunkSize, consumer);
    }

    @Override
    public <R> List<R> queryForObject(Query query, Class<R> clz) {
        QueryResult result = query(query);
        return objMapper.toPOJO(result, clz);
    }

    @Override
    public <R> List<R> queryForObject(Query query, TimeUnit timeUnit, Class<R> clz) {
        QueryResult result = query(query, timeUnit);
        return objMapper.toPOJO(result, clz);
    }

    @Override
    public List<Map<String, Object>> queryForList(Query query) {
        QueryResult result = query(query);
        return mapMapper.toMap(result);
    }

    @Override
    public Pong ping() {
        return getConnection().ping();
    }

    @Override
    public String version() {
        return getConnection().version();
    }
}
