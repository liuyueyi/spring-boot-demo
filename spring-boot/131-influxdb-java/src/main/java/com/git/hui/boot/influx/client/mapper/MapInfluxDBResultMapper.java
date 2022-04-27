package com.git.hui.boot.influx.client.mapper;

import com.git.hui.boot.influx.client.common.TimeUtil;
import org.influxdb.InfluxDBMapperException;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Series;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class MapInfluxDBResultMapper {

    public List<Map<String, Object>> toMap(QueryResult queryResult) throws InfluxDBMapperException {
        Objects.requireNonNull(queryResult, "queryResult");
        this.throwExceptionIfResultWithError(queryResult);
        List<Map<String, Object>> result = new LinkedList<>();
        queryResult.getResults().stream().filter((internalResult) -> Objects.nonNull(internalResult) &&
                Objects.nonNull(internalResult.getSeries())).forEach(
                (internalResult) -> internalResult.getSeries().forEach((series) -> parseSeriesAs(series, result)));
        return result;
    }

    private void throwExceptionIfResultWithError(QueryResult queryResult) {
        if (queryResult.getError() != null) {
            throw new InfluxDBMapperException("InfluxDB returned an error: " + queryResult.getError());
        } else {
            queryResult.getResults().forEach((seriesResult) -> {
                if (seriesResult.getError() != null) {
                    throw new InfluxDBMapperException(
                            "InfluxDB returned an error with Series: " + seriesResult.getError());
                }
            });
        }
    }

    private List<Map<String, Object>> parseSeriesAs(Series series, List<Map<String, Object>> result) {
        int columnSize = series.getColumns().size();
        Map<String, String> tagMap = series.getTags();

        String column;
        Object value;
        Map<String, Object> object;
        for (List<Object> row : series.getValues()) {
            object = new HashMap<>(columnSize + 4);
            for (int i = 0; i < columnSize; ++i) {
                column = series.getColumns().get(i);
                value = row.get(i);
                if (timeColumn(column)) {
                    value = TimeUtil.str2timestamp(String.valueOf(value));
                }
                object.put(column, value);
            }

            if (!CollectionUtils.isEmpty(tagMap)) {
                for (Map.Entry<String, String> entry : tagMap.entrySet()) {
                    object.put(entry.getKey(), entry.getValue());
                }
            }

            result.add(object);
        }

        return result;
    }

    private boolean timeColumn(String column) {
        return "time".equals(column);
    }
}
