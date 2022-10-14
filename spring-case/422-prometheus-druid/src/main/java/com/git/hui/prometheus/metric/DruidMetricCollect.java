package com.git.hui.prometheus.metric;

import com.alibaba.druid.pool.DruidDataSource;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.List;
import java.util.function.ToDoubleFunction;

/**
 * druid 对应的可直接引入面板： https://grafana.com/grafana/dashboards/11157
 *
 * @author YiHui
 * @date 2022/10/12
 */
public class DruidMetricCollect {
    private final List<DruidDataSource> dataSources;
    private final MeterRegistry registry;

    public DruidMetricCollect(List<DruidDataSource> dataSources, MeterRegistry registry) {
        this.registry = registry;
        this.dataSources = dataSources;
    }

    /**
     * 注册一些关键指标
     */
    public void register() {
        this.dataSources.forEach((druidDataSource) -> {
            // basic configurations
            createGauge(druidDataSource, "druid_initial_size", "Initial size", (datasource) -> (double) druidDataSource.getInitialSize());
            createGauge(druidDataSource, "druid_min_idle", "Min idle", datasource -> (double) druidDataSource.getMinIdle());
            createGauge(druidDataSource, "druid_max_active", "Max active", datasource -> (double) druidDataSource.getMaxActive());
            // connection pool core metrics
            createGauge(druidDataSource, "druid_active_count", "Active count", datasource -> (double) druidDataSource.getActiveCount());
            createGauge(druidDataSource, "druid_active_peak", "Active peak", datasource -> (double) druidDataSource.getActivePeak());
            createGauge(druidDataSource, "druid_pooling_peak", "Pooling peak", datasource -> (double) druidDataSource.getPoolingPeak());
            createGauge(druidDataSource, "druid_pooling_count", "Pooling count", datasource -> (double) druidDataSource.getPoolingCount());
            createGauge(druidDataSource, "druid_wait_thread_count", "Wait thread count", datasource -> (double) druidDataSource.getWaitThreadCount());
            // connection pool detail metrics
            createGauge(druidDataSource, "druid_not_empty_wait_count", "Not empty wait count", datasource -> (double) druidDataSource.getNotEmptyWaitCount());
            createGauge(druidDataSource, "druid_not_empty_wait_millis", "Not empty wait millis", datasource -> (double) druidDataSource.getNotEmptyWaitMillis());
            createGauge(druidDataSource, "druid_not_empty_thread_count", "Not empty thread count", datasource -> (double) druidDataSource.getNotEmptyWaitThreadCount());
            createGauge(druidDataSource, "druid_logic_connect_count", "Logic connect count", datasource -> (double) druidDataSource.getConnectCount());
            createGauge(druidDataSource, "druid_logic_close_count", "Logic close count", datasource -> (double) druidDataSource.getCloseCount());
            createGauge(druidDataSource, "druid_logic_connect_error_count", "Logic connect error count", datasource -> (double) druidDataSource.getConnectErrorCount());
            createGauge(druidDataSource, "druid_physical_connect_count", "Physical connect count", datasource -> (double) druidDataSource.getCreateCount());
            createGauge(druidDataSource, "druid_physical_close_count", "Physical close count", datasource -> (double) druidDataSource.getDestroyCount());
            createGauge(druidDataSource, "druid_physical_connect_error_count", "Physical connect error count", datasource -> (double) druidDataSource.getCreateErrorCount());
            // sql execution core metrics
            createGauge(druidDataSource, "druid_error_count", "Error count", datasource -> (double) druidDataSource.getErrorCount());
            createGauge(druidDataSource, "druid_execute_count", "Execute count", datasource -> (double) druidDataSource.getExecuteCount());
            // transaction metrics
            createGauge(druidDataSource, "druid_start_transaction_count", "Start transaction count", datasource -> (double) druidDataSource.getStartTransactionCount());
            createGauge(druidDataSource, "druid_commit_count", "Commit count", datasource -> (double) druidDataSource.getCommitCount());
            createGauge(druidDataSource, "druid_rollback_count", "Rollback count", datasource -> (double) druidDataSource.getRollbackCount());
            // sql execution detail
            createGauge(druidDataSource, "druid_prepared_statement_open_count", "Prepared statement open count", datasource -> (double) druidDataSource.getPreparedStatementCount());
            createGauge(druidDataSource, "druid_prepared_statement_closed_count", "Prepared statement closed count", datasource -> (double) druidDataSource.getClosedPreparedStatementCount());
            createGauge(druidDataSource, "druid_ps_cache_access_count", "PS cache access count", datasource -> (double) druidDataSource.getCachedPreparedStatementAccessCount());
            createGauge(druidDataSource, "druid_ps_cache_hit_count", "PS cache hit count", datasource -> (double) druidDataSource.getCachedPreparedStatementHitCount());
            createGauge(druidDataSource, "druid_ps_cache_miss_count", "PS cache miss count", datasource -> (double) druidDataSource.getCachedPreparedStatementMissCount());
            createGauge(druidDataSource, "druid_execute_query_count", "Execute query count", datasource -> (double) druidDataSource.getExecuteQueryCount());
            createGauge(druidDataSource, "druid_execute_update_count", "Execute update count", datasource -> (double) druidDataSource.getExecuteUpdateCount());
            createGauge(druidDataSource, "druid_execute_batch_count", "Execute batch count", datasource -> (double) druidDataSource.getExecuteBatchCount());
            // none core metrics, some are static configurations
            createGauge(druidDataSource, "druid_max_wait", "Max wait", datasource -> (double) druidDataSource.getMaxWait());
            createGauge(druidDataSource, "druid_max_wait_thread_count", "Max wait thread count", datasource -> (double) druidDataSource.getMaxWaitThreadCount());
            createGauge(druidDataSource, "druid_login_timeout", "Login timeout", datasource -> (double) druidDataSource.getLoginTimeout());
            createGauge(druidDataSource, "druid_query_timeout", "Query timeout", datasource -> (double) druidDataSource.getQueryTimeout());
            createGauge(druidDataSource, "druid_transaction_query_timeout", "Transaction query timeout", datasource -> (double) druidDataSource.getTransactionQueryTimeout());
        });
    }

    private void createGauge(DruidDataSource dataSource, String metric, String help, ToDoubleFunction<DruidDataSource> measure) {
        Gauge.builder(metric, dataSource, measure)
                .description(help)
                .tag("pool", getDbName(dataSource))
                .register(this.registry);
    }

    private String getDbName(DruidDataSource dataSource) {
        // 去掉jdbc: 针对后面的进行解析
        URI uri = URI.create(dataSource.getUrl().substring(5));
        String dbName = uri.getPath();
        if (StringUtils.isEmpty(dbName) || "/".equalsIgnoreCase(dbName)) {
            return dataSource.getName();
        }

        return StringUtils.replace(dbName, "/", "");
    }
}
