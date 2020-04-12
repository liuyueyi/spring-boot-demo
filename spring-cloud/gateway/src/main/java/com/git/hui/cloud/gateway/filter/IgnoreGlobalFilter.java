package com.git.hui.cloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 过滤一些内部接口，避免安全问题
 * Created by @author yihui in 20:46 20/4/12.
 */
@Component
public class IgnoreGlobalFilter implements GlobalFilter, Ordered {
    private static final Pattern FILTER_PATTERN = Pattern.compile("^/+[^/]+/actuator/");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put("gateway", "gateway");
        URI uri = exchange.getRequest().getURI();
        Matcher m = FILTER_PATTERN.matcher(uri.getPath());
        if (m.find()) {
            // 忽略掉
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return Mono.empty();
        } else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
