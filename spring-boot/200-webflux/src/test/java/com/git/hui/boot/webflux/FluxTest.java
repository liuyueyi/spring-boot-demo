package com.git.hui.boot.webflux;

import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * Created by @author yihui in 12:04 18/10/22.
 */
public class FluxTest {

    @Test
    public void testGet() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<String> resp = webClient.get().uri("/time").retrieve().bodyToMono(String.class);
        resp.subscribe(System.out::print);

        // 可以做其他的事情
        for (int i = 0; i < 20; i++) {
            System.out.println("1 + 1 = 2");
            TimeUnit.MILLISECONDS.sleep(100);
        }

        TimeUnit.SECONDS.sleep(10);
    }

}
