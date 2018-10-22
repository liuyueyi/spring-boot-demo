package com.git.hui.boot.webflux.action;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Created by @author yihui in 18:53 18/10/19.
 */
@Component
public class ShowAction {

    public Mono<ServerResponse> showTime(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);
    }

    public Mono<ServerResponse> showDate(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("Date is " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())), String.class);
    }

    /**
     * sse 服务端推送模式, 每隔一秒向客户端推送一次数据
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.interval(Duration.ofSeconds(1))
                .map(l -> new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);
    }
}
