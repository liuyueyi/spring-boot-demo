package com.git.hui.boot.multi.datasource.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author yihui
 * @date 20/12/27
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dataBaseType = DataSourceType.getDataBaseType();
        return dataBaseType;
    }
}
