package com.git.hui.cloud.gateway.filter;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

import java.net.URI;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 过滤一些内部接口，避免安全问题
 * Created by @author yihui in 20:46 20/4/12.
 */
@Component
public class IgnoreGlobalFilter implements GlobalFilter, Ordered {
    private static final Pattern FILTER_PATTERN = Pattern.compile("^/+[^/]+/actuator/");

    private static final byte[] bytes =
            ("<html><body><h1>Whitelabel Error Page</h1><p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p><div>There was an unexpected error (type=Not Found, status=404).</div><div>No message available</div></body></html>")
                    .getBytes();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        Matcher m = FILTER_PATTERN.matcher(uri.getPath());
        if (m.find()) {
            // 忽略掉
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return response.writeWith(Mono.just(new DefaultDataBufferFactory().wrap(bytes)));
        } else {
            // 添加自定义的请求头
            Consumer<HttpHeaders> httpHeaders = httpHeader -> httpHeader.set("gateway", "spring-cloud-gateway");
            ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders).build();
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
