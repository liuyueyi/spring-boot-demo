package com.git.hui.boot.multi.datasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author yihui
 * @date 20/12/27
 */
@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "storyDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.story")
    public DataSourceProperties storyDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "storyDataSource")
    public DataSource storyDataSource(@Qualifier("storyDataSourceProperties") DataSourceProperties storyDataSourceProperties) {
        return storyDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean("storyJdbcTemplate")
    public JdbcTemplate storyJdbcTemplate(@Qualifier("storyDataSource") DataSource storyDataSource) {
        return new JdbcTemplate(storyDataSource);
    }

    @Bean(name = "testDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.test")
    public DataSourceProperties testDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "testDataSource")
    public DataSource testDataSource(@Qualifier("testDataSourceProperties") DataSourceProperties testDataSourceProperties) {
        return testDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("testJdbcTemplate")
    public JdbcTemplate testJdbcTemplate(@Qualifier("testDataSource") DataSource testDataSource) {
        return new JdbcTemplate(testDataSource);
    }
}

