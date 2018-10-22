package com.git.hui.boot.webflux.config;

import com.git.hui.boot.webflux.action.ShowAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

/**
 * Created by @author yihui in 18:57 18/10/19.
 */
@Configuration
public class RouterConfig {
    @Autowired
    private ShowAction showAction;

    @Bean
    public RouterFunction<ServerResponse> timerRouter() {
        return RouterFunctions.route(RequestPredicates.GET("/time"), showAction::showTime)
                .andRoute(RequestPredicates.GET("/date"), showAction::showDate)
                .andRoute(RequestPredicates.GET("/times"), showAction::sendTimePerSec);
    }

}
