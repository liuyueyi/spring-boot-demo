package com.git.hui.boot.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by @author yihui in 12:04 18/10/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class FluxTest {

    @Autowired
    private Environment environment;

    @Test
    public void testGet() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<String> resp = webClient.get().uri("/time").retrieve().bodyToMono(String.class);
        resp.subscribe(System.out::println);

        // 可以做其他的事情
        for (int i = 0; i < 20; i++) {
            System.out.println("1 + 1 = 2");
            TimeUnit.MILLISECONDS.sleep(100);
        }

        TimeUnit.SECONDS.sleep(10);
    }


    @Test
    public void testSse() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        Flux<ServerSentEvent> resp = webClient.get().uri("/time").accept(MediaType.TEXT_EVENT_STREAM).retrieve()
                .bodyToFlux(ServerSentEvent.class);
        resp.map(ServerSentEvent::data).subscribe(System.out::println);

        //        Flux<ServerSentEvent> resp =
        //                webClient.get().uri("/base/loop?name=一灰灰").accept(MediaType.TEXT_EVENT_STREAM).exchange().flatMapMany(
        //                        response -> response
        //                                .body(BodyExtractors.toFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {
        //                                })));
        //
        //        resp.filter(sse -> Objects.nonNull(sse.data())).map(ServerSentEvent::data).buffer(3)
        //                .subscribe(System.out::println);

        System.out.println("do another thing");

        TimeUnit.SECONDS.sleep(100);
    }


    /**
     * buffer 最主要的作用就是把流中的元素封装为一个集合，作为流中新的元素
     */
    @Test
    public void testBuffer() {
        // buffer 表示每次从流中取20个数据封装为一个集合，然后作为流的新元素，给后面的使用
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        System.out.println("-----------------");

        // bufferTimeout 指定数量和超时时间，封装为一个集合，然后作为流的新元素，给后面的使用
        Flux.interval(Duration.ofMillis(100)).bufferTimeout(5, Duration.ofMillis(300)).take(5).toStream()
                .forEach(System.out::println);
        System.out.println("-----------------");

        // bufferUntil 当满足条件则表示集合封装完毕，可以开始下一次的收集了
        Flux.range(0, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("-----------------");

        // bufferWhile 只有满足条件的才会放在集合中; 如果为false，则开始下一次的收集
        Flux.range(0, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
    }

    /**
     * filter 最主要的作用就是过滤不满足要求的元素
     */
    @Test
    public void testFilter() {
        // filter 表示只收集满足条件的元素
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
    }

    /**
     * take 最主要的就是从流中获取数据
     */
    @Test
    public void testTake() {
        // take 从流中获取多少个数据；参数也可以传入时间，表示按照指定的时间来获取数据
        Flux.range(1, 100).take(10).subscribe(s -> System.out.print(s + ","));
        System.out.println("\n--------------------");

        // takeLast 表示获取最后多少个数据
        Flux.range(1, 100).takeLast(10).subscribe(s -> System.out.print(s + ","));
        System.out.println("\n--------------------");

        // takeWhile 根据后面表达式，为true，则获取数据；如果为false，则终止
        Flux.range(1, 100).takeWhile(i -> i < 10).subscribe(s -> System.out.print(s + ","));
        System.out.println("\n--------------------");

        // takeUntil 直到后面返回true，则退出
        Flux.range(1, 100).takeUntil(i -> i == 10).subscribe(s -> System.out.print(s + ","));
        System.out.println("\n--------------------");
    }

    /**
     * reduce 对流中的元素进行累积操作，得到一个包含计算结果的Mono序列
     */
    @Test
    public void testReduce() {
        // reduce，对流中每个元素和前面计算结果进行计算，最后输出一个结果
        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
        System.out.println("-----------------");

        // reduceWith 相对于reduce而言，给了一个初始值
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);
        System.out.println("-----------------");
    }


    /**
     * merge 就是用来将多个流合并为一个Flux序列
     */
    @Test
    public void testMerge() {
        // merge 用来合并多个流
        Flux.merge(Flux.interval(Duration.ofMillis(100)).take(5),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5)).toStream()
                .forEach(s -> System.out.print(s + ","));
        System.out.println("\n-------------------");

        // mergeSequential 与前面的区别是这个是根据流的顺序来合并
        Flux.mergeSequential(Flux.interval(Duration.ofMillis(100)).take(5),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5)).toStream()
                .forEach(s -> System.out.print(s + ","));
        System.out.println("\n-------------------");
    }


    /**
     * flapMap 最大的作用就是把每个元素，都封装成流，然后将流进行合并
     */
    @Test
    public void testFlatMap() {
        // flatMap 将每个元素封装为流，最后把所有的流合并在一起，可能是乱序的
        Flux.just(5, 10).flatMap(x -> Flux.just(x + x)).subscribe(s -> System.out.print(s + ","));
        System.out.println("\n-------------------");

        Flux.just(5, 10).flatMap(x -> Flux.interval(Duration.ofMillis(100)).take(x)).toStream()
                .forEach(s -> System.out.print(s + ","));
        System.out.println("\n-------------------");

        // flatMapSequential 相对于前面的而言，是有序的
        Flux.just(5, 10).flatMapSequential(x -> Flux.interval(Duration.ofMillis(100)).take(x)).toStream()
                .forEach(s -> System.out.print(s + ","));
        System.out.println("\n-------------------");


        // 从结果上和前面一样，区别在于concatMap 对转换之后的流的订阅是动态进行的，而 flatMapSequential 在合并之前就已经订阅了所有的流
        Flux.just(5, 10).concatMap(x -> Flux.interval(Duration.ofMillis(100)).take(x)).toStream()
                .forEach(s -> System.out.print(s + ","));
        System.out.println("\n-------------------");


        // 一个链式的操作，实现 x + x -> x*x
        Flux.just(2, 3).flatMap(x -> Flux.just(x + x)).flatMap(x -> Flux.just(x * x))
                .subscribe(s -> System.out.print(s + ","));
        System.out.println("\n-------------------");

        // map 与flatMap的区别，在于内部方法的返回为普通对象；前者返回的是Publisher
        Flux.just(2, 3).map(x -> x + x).map(x -> x * x).subscribe(s -> System.out.print(s + ","));
        System.out.println("\n-------------------");
    }


}
