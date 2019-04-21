package com.git.hui.boot.webflux.action;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


/**
 * Created by @author yihui in 17:12 19/3/14.
 */
@RestController
@RequestMapping(path = "base")
public class BasicAction {

    @GetMapping(path = "hello")
    public Mono<String> sayHello(@RequestParam("name") String name) {
        return Mono.just("hello " + name);
    }


    @GetMapping(path = "loop")
    public Flux<ServerSentEvent<String>> everySayHello(@RequestParam("name") String name) {
        return Flux.interval(Duration.ofSeconds(1)).map(seed -> seed + seed)
                .map(s -> ServerSentEvent.<String>builder().event("rand").data(name + "|" + s).build());
    }

}
