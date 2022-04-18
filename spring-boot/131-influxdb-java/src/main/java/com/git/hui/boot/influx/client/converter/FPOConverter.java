package com.git.hui.boot.influx.client.converter;

import lombok.Data;
import com.git.hui.boot.influx.client.exception.ConverterException;
import com.git.hui.boot.influx.client.modal.InfluxFPO;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.Point;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by @author yihui in 09:48 19/7/25.
 */
public class FPOConverter<T> implements PointCollectionConverter<T> {
    protected PointBuilder pointBuilder = new PointBuilder();

    @Override
    public Point convert(T o) {
        return pointBuilder.build(o);
    }

    protected final static class PointBuilder {
        private static final int DECIMAL_SCALE = 13;
        private Map<Class, InnerCache> cache = new ConcurrentHashMap<>(64);

        public Point build(Object obj) {
            return build(obj, DECIMAL_SCALE);
        }

        public Point build(Object obj, int decimalScale) {
            Class<?> clazz = obj.getClass();
            InnerCache innerCache = getCache(clazz, decimalScale);
            return innerCache.newPoint(obj);
        }

        public <T> List<Point> buildList(List<T> objList) {
            return buildList(objList, DECIMAL_SCALE);
        }

        public <T> List<Point> buildList(List<T> objList, final int decimalScale) {
            List<Point> list = new ArrayList<>(objList.size());
            for (T t : objList) {
                list.add(build(t, decimalScale));
            }
            return list;
        }

        private InnerCache getCache(Class<?> clazz, int decimalScale) {
            InnerCache innerCache = cache.get(clazz);
            if (innerCache == null) {
                synchronized (PointBuilder.class) {
                    innerCache = cache.get(clazz);
                    if (innerCache == null) {
                        innerCache = newCache(clazz, decimalScale);
                        cache.put(clazz, innerCache);
                    }
                }
            }

            return innerCache;
        }

        private InnerCache newCache(Class<?> clazz, int scale) {
            String measurement = clazz.getAnnotation(Measurement.class).name();
            if (StringUtils.isEmpty(measurement)) {
                measurement = null;
            }

            InnerCache cache = new InnerCache(measurement, scale);
            while (clazz != Object.class) {
                buildInnerCache(clazz, cache);
                clazz = clazz.getSuperclass();
            }

            if (cache.tagFields.isEmpty()) {
                throw new ConverterException(
                        "Class " + clazz.getName() + " has no @" + Column.class.getSimpleName() + " annotation");
            }
            return cache;
        }

        private void buildInnerCache(Class clazz, InnerCache cache) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    field.setAccessible(true);
                    String fieldName = column.name();
                    if ("time".equals(fieldName)) {
                        cache.setTime(field);
                    } else if (column.tag()) {
                        cache.tagFields.put(fieldName, field);
                    } else {
                        cache.valueFields.put(fieldName, field);
                    }
                }
            }
        }
    }

    @Data
    private final static class InnerCache {
        private volatile String measurement;
        private int scale;
        private Field time;
        private Map<String, Field> tagFields;
        private Map<String, Field> valueFields;

        private InnerCache(String measurement, int scale) {
            this.measurement = measurement;
            this.scale = scale;
            this.tagFields = new HashMap<>(4);
            this.valueFields = new HashMap<>(8);
        }

        private void tryInitMeasurement(Object obj) {
            if (measurement != null) {
                return;
            }

            if (obj instanceof InfluxFPO) {
                synchronized (this) {
                    if (measurement == null) {
                        measurement = ((InfluxFPO) obj).measurement();
                    }
                }
            }

            throw new ConverterException("Class " + obj.getClass().getName() + " is not annotated with @Measurement " +
                    "or not implements method measurement()");
        }

        private Point newPoint(Object obj) {
            try {
                tryInitMeasurement(obj);
                Point.Builder builder = Point.measurement(measurement);
                if (this.time != null) {
                    builder.time((Long) time.get(obj), TimeUnit.MILLISECONDS);
                }

                for (Map.Entry<String, Field> me : tagFields.entrySet()) {
                    builder.tag(me.getKey(), me.getValue().get(obj).toString());
                }

                for (Map.Entry<String, Field> me : valueFields.entrySet()) {
                    builder.field(me.getKey(), check(me.getValue().get(obj)));
                }
                return builder.build();
            } catch (Exception e) {
                throw new ConverterException("convert obj to point error! obj: " + obj);
            }
        }

        private Object check(Object fieldValue) {
            if (fieldValue instanceof BigDecimal) {
                if (((BigDecimal) fieldValue).scale() > scale) {
                    fieldValue = ((BigDecimal) fieldValue).setScale(scale, RoundingMode.HALF_UP);
                }
            }
            return fieldValue;
        }
    }
}
