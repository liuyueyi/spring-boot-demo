package com.git.hui.boot.multi.datasource.test;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 实现方式参考自: <a href="https://blog.csdn.net/tuesdayma/article/details/81081666"/>
 *
 * @author yihui
 * @date 20/12/27
 */
@Configuration
@MapperScan(basePackages = "com.git.hui.boot.multi.datasource.test.mapper", sqlSessionFactoryRef = "testSqlSessionFactory")
public class TestDatasourceConfig {

    @Bean(name = "testDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.test")
    public DataSourceProperties testDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "testDataSource")
    public DataSource testDataSource(@Qualifier("testDataSourceProperties") DataSourceProperties storyDataSourceProperties) {
        return storyDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("testSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("testDataSource") DataSource testDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(testDataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test/*.xml"));
        return bean.getObject();
    }

    @Bean("testSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("testSqlSessionFactory") SqlSessionFactory testSqlSessionFactory) {
        return new SqlSessionTemplate(testSqlSessionFactory);
    }
}
