package com.git.hui.boot.influx.client.converter.series;

/**
 * Created by @author yihui in 10:17 19/7/16.
 */
public class DoubleSeriesConverter implements SeriesConverter<Double> {
    @Override
    public boolean enabled(Class<?> fieldType, Object... args) {
        return double.class.isAssignableFrom(fieldType) || Double.class.isAssignableFrom(fieldType);
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public Double convert(Object s) {
        return Double.valueOf(String.valueOf(s));
    }
}
