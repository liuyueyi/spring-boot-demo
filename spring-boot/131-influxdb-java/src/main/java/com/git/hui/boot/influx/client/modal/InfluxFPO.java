package com.git.hui.boot.influx.client.modal;

import org.influxdb.annotation.Measurement;

import java.io.Serializable;

/**
 * 所有influx表中对应的point，请继承自这个接口
 * Created by @author yihui in 09:48 19/7/25.
 */
public interface InfluxFPO extends Serializable {

    /**
     * fpo对应的measurement
     *
     * @return
     */
    default String measurement() {
        Measurement measurement = this.getClass().getAnnotation(Measurement.class);
        return measurement == null ? null : measurement.name();
    }
}
