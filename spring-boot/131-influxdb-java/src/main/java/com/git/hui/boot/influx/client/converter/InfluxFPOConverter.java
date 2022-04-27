package com.git.hui.boot.influx.client.converter;

import com.git.hui.boot.influx.client.modal.InfluxFPO;
import org.influxdb.dto.Point;

/**
 * Created by @author yihui in 09:48 19/7/25.
 */
public class InfluxFPOConverter extends FPOConverter<InfluxFPO> {
    @Override
    public Point convert(InfluxFPO o) {
        return pointBuilder.build(o);
    }
}
