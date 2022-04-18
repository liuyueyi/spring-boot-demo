package com.git.hui.boot.influx.client.converter.series;

/**
 * Created by @author yihui in 10:17 19/7/16.
 */
public class LongSeriesConverter implements SeriesConverter<Long> {
    @Override
    public boolean enabled(Class<?> fieldType, Object... args) {
        return long.class.isAssignableFrom(fieldType) || Long.class.isAssignableFrom(fieldType);
    }

    @Override
    public int order() {
        return 4;
    }

    @Override
    public Long convert(Object s) {
        if (s instanceof Double) {
            return ((Double) s).longValue();
        }

        return Long.valueOf(String.valueOf(s));
    }
}
