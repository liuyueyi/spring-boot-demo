//package com.git.hui.boot.jdbc;
//
//import com.git.hui.boot.jdbc.insert.ExtendJdbcTemplate;
//import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * Created by @author yihui in 21:54 19/4/7.
// */
//@Configuration
//public class AutoConfig {
//
//    @Bean
//    public ExtendJdbcTemplate extendJdbcTemplate(DataSource dataSource, JdbcProperties properties) {
//        ExtendJdbcTemplate jdbcTemplate = new ExtendJdbcTemplate(dataSource);
//        JdbcProperties.Template template = properties.getTemplate();
//        jdbcTemplate.setFetchSize(template.getFetchSize());
//        jdbcTemplate.setMaxRows(template.getMaxRows());
//        if (template.getQueryTimeout() != null) {
//            jdbcTemplate.setQueryTimeout((int) template.getQueryTimeout().getSeconds());
//        }
//
//        return jdbcTemplate;
//    }
//
//}
