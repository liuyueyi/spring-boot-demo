package com.git.hui.boot.webflux.config;

import com.git.hui.boot.webflux.action.ShowAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


/**
 * Created by @author yihui in 18:57 18/10/19.
 */
@Configuration
public class RouterConfig {
    @Autowired
    private ShowAction showAction;

    @Bean
    public RouterFunction<ServerResponse> timerRouter() {
        return RouterFunctions.route(RequestPredicates.GET("/hello"), showAction::hello)
                .andRoute(RequestPredicates.GET("/time"), showAction::showTime)
                .andRoute(RequestPredicates.GET("/date"), showAction::showDate)
                .andRoute(RequestPredicates.GET("/times"), showAction::sendTimePerSec);
    }

    @Bean
    public RouterFunction<ServerResponse> swaggerRouter(
            @Value("classpath:/META-INF/resources/ui.html") final Resource indexHtml) {
        return RouterFunctions.route(RequestPredicates.GET("/ui"),
                request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml))
                .andRoute(RequestPredicates.GET("/b"),
                        request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue("b.html"));
    }

    @Bean
    public RouterFunction<ServerResponse> indexRouter(@Value("classpath:/index.html") final Resource indexHtml,
            @Value("classpath:/self/s.html") final Resource sHtml) {
        return RouterFunctions.route(RequestPredicates.GET("/index"),
                request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml))
                .andRoute(RequestPredicates.GET("/s"),
                        request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(sHtml));
    }
}
