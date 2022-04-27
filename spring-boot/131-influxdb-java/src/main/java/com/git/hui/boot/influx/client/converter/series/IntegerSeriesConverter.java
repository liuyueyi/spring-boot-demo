package com.git.hui.boot.influx.client.converter.series;

/**
 * Created by @author yihui in 10:17 19/7/16.
 */
public class IntegerSeriesConverter implements SeriesConverter<Integer> {
    @Override
    public boolean enabled(Class<?> fieldType, Object... args) {
        return int.class.isAssignableFrom(fieldType) || Integer.class.isAssignableFrom(fieldType);
    }

    @Override
    public int order() {
        return 8;
    }

    @Override
    public Integer convert(Object s) {
        if (s instanceof Double) {
            return ((Double) s).intValue();
        }

        return Integer.valueOf(String.valueOf(s));
    }
}
