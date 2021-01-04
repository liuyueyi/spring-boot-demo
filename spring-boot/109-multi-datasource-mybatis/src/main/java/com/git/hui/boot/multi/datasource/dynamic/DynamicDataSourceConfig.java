//package com.git.hui.boot.multi.datasource.dynamic;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author yihui
// * @date 20/12/27
// */
//@Configuration
//@MapperScan
//public class DynamicDataSourceConfig {
//
//    @Bean(name = "dynamicDataSource")
//    public DynamicDataSource DataSource(@Qualifier("storyDataSource") DataSource storyDataSource,
//                                        @Qualifier("testDataSource") DataSource testDataSource) {
//        Map<Object, Object> targetDataSource = new HashMap<>();
//        targetDataSource.put("story", storyDataSource);
//        targetDataSource.put("test", testDataSource);
//        DynamicDataSource dataSource = new DynamicDataSource();
//        dataSource.setTargetDataSources(targetDataSource);
//        dataSource.setDefaultTargetDataSource(storyDataSource);
//        return dataSource;
//    }
//
//    @Bean(name = "SqlSessionFactory")
//    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dynamicDataSource);
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
//        return bean.getObject();
//    }
//}
