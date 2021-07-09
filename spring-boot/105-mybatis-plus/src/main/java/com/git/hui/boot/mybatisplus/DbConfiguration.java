package com.git.hui.boot.mybatisplus;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yihui
 * @date 2021/7/5
 */
@Configuration
public class DbConfiguration {
    /**
     * 这个等价于使用 @MapperScan 这个注解
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.git.hui.boot.mybatisplus.mapper");
        return mapperScannerConfigurer;
    }
}
