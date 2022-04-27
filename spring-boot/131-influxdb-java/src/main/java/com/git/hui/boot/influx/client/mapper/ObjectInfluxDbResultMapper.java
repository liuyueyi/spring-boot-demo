package com.git.hui.boot.influx.client.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.git.hui.boot.influx.client.converter.series.*;
import org.influxdb.InfluxDBMapperException;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.QueryResult;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by @author yihui in 10:37 19/7/16.
 */
public class ObjectInfluxDbResultMapper {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static final class FieldConverterPair {
        private Field field;
        private SeriesConverter converter;
    }

    /**
     * Data structure used to cache classes used as measurements.
     */
    private static final ConcurrentMap</*** fpo class*/Class, ConcurrentMap</*** fieldName */String, FieldConverterPair>>
            CLASS_FIELD_CONVERTER_CACHE = new ConcurrentHashMap<>();
    private List<SeriesConverter> seriesConverterList;

    public ObjectInfluxDbResultMapper() {
        this.seriesConverterList = new ArrayList<>(8);
        this.seriesConverterList.add(new BigDecimalSeriesConverter());
        this.seriesConverterList.add(new BooleanSeriesConverter());
        this.seriesConverterList.add(new DoubleSeriesConverter());
        this.seriesConverterList.add(new IntegerSeriesConverter());
        this.seriesConverterList.add(new LongSeriesConverter());
        this.seriesConverterList.add(new TimestampConverter());
        this.seriesConverterList.add(new DefaultSeriesConverter());
        seriesConverterList.sort(new Comparator<SeriesConverter>() {
            @Override
            public int compare(SeriesConverter o1, SeriesConverter o2) {
                if (o1.order() == o2.order()) {
                    return 0;
                }
                return o1.order() > o2.order() ? 1 : -1;
            }
        });
    }

    public void registSeriesConverter(SeriesConverter converter) {
        seriesConverterList.add(converter);
        seriesConverterList.sort(new Comparator<SeriesConverter>() {
            @Override
            public int compare(SeriesConverter o1, SeriesConverter o2) {
                if (o1.order() == o2.order()) {
                    return 0;
                }
                return o1.order() > o2.order() ? 1 : -1;
            }
        });
    }

    /**
     * <p>
     * Process a {@link QueryResult} object returned by the InfluxDB client inspecting the internal
     * data structure and creating the respective object instances based on the Class passed as
     * parameter.
     * </p>
     *
     * @param queryResult the InfluxDB result object
     * @param clazz       the Class that will be used to hold your measurement data
     * @param <T>         the target type
     * @return a {@link List} of objects from the same Class passed as parameter and sorted on the
     * same order as received from InfluxDB.
     * @throws InfluxDBMapperException If {@link QueryResult} parameter contain errors,
     *                                 <tt>clazz</tt> parameter is not annotated with &#64;Measurement or it was not
     *                                 possible to define the values of your POJO (e.g. due to an unsupported field type).
     */
    public <T> List<T> toPOJO(final QueryResult queryResult, final Class<T> clazz) throws InfluxDBMapperException {
        throwExceptionIfMissingAnnotation(clazz);
        String measurementName = getMeasurementName(clazz);
        return this.toPOJO(queryResult, clazz, measurementName);
    }

    /**
     * <p>
     * Process a {@link QueryResult} object returned by the InfluxDB client inspecting the internal
     * data structure and creating the respective object instances based on the Class passed as
     * parameter.
     * </p>
     *
     * @param queryResult     the InfluxDB result object
     * @param clazz           the Class that will be used to hold your measurement data
     * @param <T>             the target type
     * @param measurementName name of the Measurement
     * @return a {@link List} of objects from the same Class passed as parameter and sorted on the
     * same order as received from InfluxDB.
     * @throws InfluxDBMapperException If {@link QueryResult} parameter contain errors,
     *                                 <tt>clazz</tt> parameter is not annotated with &#64;Measurement or it was not
     *                                 possible to define the values of your POJO (e.g. due to an unsupported field type).
     */
    public <T> List<T> toPOJO(final QueryResult queryResult, final Class<T> clazz, final String measurementName)
            throws InfluxDBMapperException {

        Objects.requireNonNull(measurementName, "measurementName");
        Objects.requireNonNull(queryResult, "queryResult");
        Objects.requireNonNull(clazz, "clazz");

        throwExceptionIfResultWithError(queryResult);
        cacheMeasurementClass(clazz);
        List<T> result = new LinkedList<>();


        queryResult.getResults().stream().filter(internalResult -> Objects.nonNull(internalResult) &&
                Objects.nonNull(internalResult.getSeries())).forEach(internalResult -> {
            internalResult.getSeries().stream().filter(series -> series.getName().equals(measurementName))
                    .forEachOrdered(series -> {
                        parseSeriesAs(series, clazz, result);
                    });
        });

        return result;
    }

    void cacheMeasurementClass(final Class<?>... classVarArgs) {
        for (Class<?> clazz : classVarArgs) {
            if (CLASS_FIELD_CONVERTER_CACHE.containsKey(clazz)) {
                continue;
            }

            ConcurrentMap<String, FieldConverterPair> influxColumnAndFieldMap =
                    CLASS_FIELD_CONVERTER_CACHE.computeIfAbsent(clazz, (k) -> new ConcurrentHashMap<>(8));

            Class<?> c = clazz;
            String fieldName;
            Column colAnnotation;
            while (c != null) {
                for (Field field : getClassColumnFields(c)) {
                    colAnnotation = field.getAnnotation(Column.class);
                    fieldName = colAnnotation.name();
                    for (SeriesConverter converter : seriesConverterList) {
                        if (converter.enabled(field.getType(), fieldName)) {
                            influxColumnAndFieldMap.put(fieldName, new FieldConverterPair(field, converter));
                            break;
                        }
                    }
                }
                c = c.getSuperclass();
            }
        }
    }

    /**
     * 获取类的column成员（支持父类成员的获取)
     *
     * @param clz
     * @return
     */
    final List<Field> getClassColumnFields(Class clz) {
        Set<String> fieldName = new HashSet<>(16);
        List<Field> result = new ArrayList<>(16);
        while (clz != Object.class) {
            for (Field field : clz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class) && !fieldName.contains(field.getName())) {
                    // 获取column注解的成员，如果子类覆盖了父类的成员，则过滤掉父类的成员
                    fieldName.add(field.getName());
                    result.add(field);
                }
            }
            clz = clz.getSuperclass();
        }
        return result;
    }


    final String getMeasurementName(final Class<?> clazz) {
        return clazz.getAnnotation(Measurement.class).name();
    }

    final void throwExceptionIfMissingAnnotation(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Measurement.class)) {
            throw new IllegalArgumentException(
                    "Class " + clazz.getName() + " is not annotated with @" + Measurement.class.getSimpleName());
        }
    }

    final void throwExceptionIfResultWithError(final QueryResult queryResult) {
        if (queryResult.getError() != null) {
            throw new InfluxDBMapperException("InfluxDB returned an error: " + queryResult.getError());
        }

        queryResult.getResults().forEach(seriesResult -> {
            if (seriesResult.getError() != null) {
                throw new InfluxDBMapperException("InfluxDB returned an error with Series: " + seriesResult.getError());
            }
        });
    }

    <T> List<T> parseSeriesAs(final QueryResult.Series series, final Class<T> clazz, final List<T> result) {
        int columnSize = series.getColumns().size();
        ConcurrentMap<String, FieldConverterPair> colNameAndFieldMap = CLASS_FIELD_CONVERTER_CACHE.get(clazz);
        try {
            T object = null;
            for (List<Object> row : series.getValues()) {
                for (int i = 0; i < columnSize; i++) {
                    FieldConverterPair correspondingField =
                            colNameAndFieldMap.get(series.getColumns().get(i)/*InfluxDB columnName*/);
                    if (correspondingField != null) {
                        if (object == null) {
                            object = clazz.newInstance();
                        }
                        setFieldValue(object, correspondingField, row.get(i));
                    }
                }

                // When the "GROUP BY" clause is used, "tags" are returned as Map<String,String> and
                // accordingly with InfluxDB documentation
                // https://docs.influxdata.com/influxdb/v1.2/concepts/glossary/#tag-value
                // "tag" values are always String.
                if (series.getTags() != null && !series.getTags().isEmpty()) {
                    for (Map.Entry<String, String> entry : series.getTags().entrySet()) {
                        FieldConverterPair correspondingField =
                                colNameAndFieldMap.get(entry.getKey()/*InfluxDB columnName*/);
                        if (correspondingField != null) {
                            // I don't think it is possible to reach here without a valid "object"
                            setFieldValue(object, correspondingField, entry.getValue());
                        }
                    }
                }
                if (object != null) {
                    result.add(object);
                    object = null;
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InfluxDBMapperException(e);
        }
        return result;
    }

    /**
     * InfluxDB client returns any number as Double.
     * See https://github.com/influxdata/influxdb-java/issues/153#issuecomment-259681987
     * for more information.
     *
     * @param object
     * @param fieldPair
     * @param value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    <T> void setFieldValue(final T object, final FieldConverterPair fieldPair, final Object value)
            throws IllegalArgumentException, IllegalAccessException {
        if (value == null) {
            return;
        }
        Field field = fieldPair.getField();
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(object, fieldPair.getConverter().convert(value));
        } catch (ClassCastException e) {
            String msg =
                    "Class '%s' field '%s' was defined with a different field type and caused a ClassCastException. " +
                            "The correct type is '%s' (current field value: '%s').";
            throw new InfluxDBMapperException(
                    String.format(msg, object.getClass().getName(), field.getName(), value.getClass().getName(),
                            value));
        }
    }
}
