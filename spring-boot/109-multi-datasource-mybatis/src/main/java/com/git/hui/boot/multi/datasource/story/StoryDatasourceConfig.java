package com.git.hui.boot.multi.datasource.story;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author yihui
 * @date 20/12/27
 */
@Configuration
@MapperScan(basePackages = "com.git.hui.boot.multi.datasource.story.mapper", sqlSessionFactoryRef = "storySqlSessionFactory")
public class StoryDatasourceConfig {

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
    @Bean("storySqlSessionFactory")
    public SqlSessionFactory storySqlSessionFactory(DataSource storyDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(storyDataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/story/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean("storySqlSessionTemplate")
    public SqlSessionTemplate storySqlSessionTemplate(SqlSessionFactory storySqlSessionFactory) {
        return new SqlSessionTemplate(storySqlSessionFactory);
    }
}
