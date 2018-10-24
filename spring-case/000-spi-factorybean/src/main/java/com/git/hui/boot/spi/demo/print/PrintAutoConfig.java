package com.git.hui.boot.spi.demo.print;

import com.git.hui.boot.spi.egine.SpiFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by @author yihui in 16:42 18/10/23.
 */
@Configuration
public class PrintAutoConfig {

    @Bean
    public SpiFactoryBean printSpiPoxy(ApplicationContext applicationContext) {
        return new SpiFactoryBean(applicationContext, IPrint.class);
    }

    @Bean
    @Primary
    public IPrint printProxy(SpiFactoryBean spiFactoryBean) throws Exception {
        return (IPrint) spiFactoryBean.getObject();
    }
}
