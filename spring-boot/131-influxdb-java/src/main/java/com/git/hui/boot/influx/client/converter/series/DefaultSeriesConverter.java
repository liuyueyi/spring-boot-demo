package com.git.hui.boot.influx.client.converter.series;

/**
 * Created by @author yihui in 10:24 19/7/16.
 */
public class DefaultSeriesConverter implements SeriesConverter<Object> {
    @Override
    public boolean enabled(Class<?> fieldType, Object... args) {
        return true;
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object convert(Object s) {
        return s;
    }
}
