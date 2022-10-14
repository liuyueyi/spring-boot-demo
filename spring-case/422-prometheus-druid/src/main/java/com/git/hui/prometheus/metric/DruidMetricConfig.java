package com.git.hui.prometheus.metric;

import com.alibaba.druid.pool.DruidDataSource;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author YiHui
 * @date 2022/10/12
 */
@Configuration
@ConditionalOnClass({DruidDataSource.class, MeterRegistry.class})
public class DruidMetricConfig {
    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    public void bind(Collection<DataSource> dataSources) throws SQLException {
        List<DruidDataSource> druidDataSources = new ArrayList<>(dataSources.size());
        for (DataSource dataSource : dataSources) {
            DruidDataSource druidDataSource = dataSource.unwrap(DruidDataSource.class);
            if (druidDataSource != null) {
                druidDataSources.add(druidDataSource);
            }
        }
        DruidMetricCollect druidCollector = new DruidMetricCollect(druidDataSources, meterRegistry);
        druidCollector.register();
    }
}
