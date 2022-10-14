package com.git.hui.druid.extend;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * druid 数据源的扩展，可以打印每个连接的信息；也可以在这里上报连接相关的信息
 *
 * @author YiHui
 * @date 2022/10/12
 */
public class MyDruidDataSource extends DruidDataSourceWrapper implements InitializingBean {
    @Resource
    private PrometheusMeterRegistry meterRegistry;

    @Override
    public DruidPooledConnection getConnection() throws SQLException {
        DruidPooledConnection connection = this.getConnection(this.maxWait);
        System.out.println(LocalDateTime.now() + " : " + connection.getConnectionHolder());
        return connection;
    }

    /**
     * 如果db密码是通过加密的方式卸载配置文件中，则可以在这里进行解密处理
     *
     * @param password
     */
    @Override
    public void setPassword(String password) {
        // 密码解密
        super.setPassword(password);
    }

    /**
     * 数据库的监控埋点上报，这里只做一个简单的示例；更多的请参考
     * spring-case/421-prometheus-druid 的接入
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        //当前活跃连接数
        Gauge.builder("ds.pool.active_count", this::getActiveCount).tag("db_name", getDbName()).register(meterRegistry);
        //最大活跃连接数
        Gauge.builder("ds.pool.max_active", this::getMaxActive).tag("db_name", getDbName()).register(meterRegistry);
        //在连接池中空闲的连接数
        Gauge.builder("ds.pool.pooling_count", this::getPoolingCount).tag("db_name", getDbName()).register(meterRegistry);
        //当前等待线程数
        Gauge.builder("ds.pool.wait_thread_count", this::getWaitThreadCount).tag("db_name", getDbName()).register(meterRegistry);
        //最大等待线程数
        Gauge.builder("ds.pool.max_wait_thread_count", this::getMaxWaitThreadCount).tag("db_name", getDbName()).register(meterRegistry);
        //已执行的SQL数量
        Gauge.builder("ds.pool.execute_count", this::getExecuteCount).tag("db_name", getDbName()).register(meterRegistry);
        // 查询执行数量
        Gauge.builder("ds.pool.execute_query_count", this::getExecuteQueryCount).tag("db_name", getDbName()).register(meterRegistry);
        // 更新执行数量
        Gauge.builder("ds.pool.execute_update_count", this::getExecuteUpdateCount).tag("db_name", getDbName()).register(meterRegistry);
    }

    private String getDbName() {
        // 去掉jdbc: 针对后面的进行解析
        URI uri = URI.create(this.getUrl().substring(5));
        String dbName = uri.getPath();
        if (StringUtils.isEmpty(dbName) || "/".equalsIgnoreCase(dbName)) {
            return this.getName();
        }

        return StringUtils.replace(dbName, "/", "");
    }
}
