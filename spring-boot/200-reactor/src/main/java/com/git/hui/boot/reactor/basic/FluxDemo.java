package com.git.hui.boot.reactor.basic;

import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by @author yihui in 10:15 20/5/28.
 */
@Component
public class FluxDemo {
    private Consumer<Object> cellConsumer(String tag, long startTime) {
        return (o) -> {
            long cost = System.currentTimeMillis() - startTime;
            System.out.println(Thread.currentThread() + " [" + tag + "]  >>> consumer ans: " + o + " cost: " + cost);
        };
    }

    private Consumer<Object> cellConsumer(String tag) {
        return (o) -> System.out.println(Thread.currentThread() + " [" + tag + "]  >>> consumer ans: " + o);
    }

    private Consumer<Object> errorConsumer(String tag) {
        return (o) -> System.out.println(Thread.currentThread() + " [" + tag + "]  >>> error msg: " + o);
    }

    private Runnable onComplete(String tag) {
        return () -> System.out.println(Thread.currentThread() + " [" + tag + "]  >>> over!");
    }

    private String tag(String tag) {
        System.out.println("\n");
        return tag;
    }

    public void basicTest() {
        //        create();
        //        parser();
        filter();
        pick();
        insert();
    }

    public void create() {
        System.out.println("========== create() ===============");
        String tag = tag("just");
        Flux.just("hello", "world").subscribe(this.cellConsumer(tag));


        tag = tag("fromArray");
        Flux.fromArray(new String[]{"hello", "world"}).subscribe(this.cellConsumer(tag));


        tag = tag("fromIterable");
        Flux.fromIterable(Arrays.asList("hello", "world")).subscribe(this.cellConsumer(tag));


        tag = tag("fromStream");
        Flux.fromStream(Stream.of("hello", "world")).subscribe(this.cellConsumer(tag));


        // 只有完成信号的空数据流
        tag = tag("empty");
        Flux.empty().subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // 只有错误的数据流
        tag = tag("error");
        Flux.error(new RuntimeException("error"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));


        // 懒汉方式，只有订阅时才创建数据源对象
        tag = tag("defer");
        Flux<Object> f = Flux.defer(() -> Flux.just("hello world", new Date()));
        f.subscribe(this.cellConsumer(tag));
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        f.subscribe(this.cellConsumer(tag));


        // 创建方式，比如内部可以绑定一个输入框的事件，当输入框输入文本时，将文本内容投递给消费端
        // create 提供的是整个序列的产生逻辑，sink.next()可以调用多次(异步通知)
        // todo 这个异步通知和下面的同步通知差异性体现在哪？
        tag = tag("create");
        Flux.create((s) -> {
            s.next("hello");
            s.next("world");
            s.next(520);
            s.complete();
        }).subscribe(this.cellConsumer(tag));


        // generate 只提供序列中单个消息的产生逻辑(同步通知)，其中的 sink.next()最多只能调用一次
        tag = tag("generate");
        Flux.generate((s) -> {
            s.next("hello world");
            s.complete();
        }).subscribe(this.cellConsumer(tag));


        // 从start开始，依次+1，共输出count个数字
        tag = tag("range");
        Flux.range(10, 5).subscribe(this.cellConsumer(tag));


        // 每隔多长时间产生一个数据
        tag = tag("interval");
        Disposable disposable =
                Flux.interval(Duration.ofMillis(100)).map(s -> "cnt: " + s).take(10).subscribe(this.cellConsumer(tag));
        while (!disposable.isDisposed()) {
        }

        // 如果数据是从一个需要回收的资源里面产生的， 第一个参数产生资源，第二个参数从资源中获取数据，第三个参数回收资源
        tag = tag("using");
        Flux.using(() -> new BufferedReader(
                        new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("demo.txt"))),
                (s) -> Flux.create(flink -> {
                    String line = null;
                    try {
                        line = s.readLine();
                        while (line != null) {
                            flink.next(line);
                            line = s.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    flink.complete();
                }), (s) -> {
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).subscribe(this.cellConsumer(tag));
        System.out.println("\n");
    }


    public void parser() {
        // 序列转换
        System.out.println("========== parser() ===============");

        // 依次将序列中的元素进行规则转换
        String tag = tag("map");
        Flux.just("123", "456", "22").map(s -> s + "_len=" + s.length()).subscribe(this.cellConsumer(tag));


        // 单纯的类型转换，请注意如果不能完成的蕾西
        tag = tag("cast");
        List list = Arrays.asList(123, 456, 7);
        Flux.fromIterable(list).cast(Integer.class).doOnNext(s -> System.out.println(s + " instance: " + s.getClass()))
                .subscribe(this.cellConsumer(tag));


        // index 获取元素的序号，并封装一个 Tuple2 对象投递给消费者
        tag = tag("index");
        Flux.just("hello", "world").index().subscribe(this.cellConsumer(tag));


        // handle 可以实现 1对n 转换
        tag = tag("handle");
        Flux.just("hello", "world").handle((cell, sink) -> {
            sink.next(cell.length());
            sink.complete();
        }).subscribe(this.cellConsumer(tag));

        // flatMap 支持异步的转换
        tag = tag("flatMap");
        Flux.just("hello", "world").flatMap(s -> Flux.just(str2list(s))).subscribe(this.cellConsumer(tag));

        tag = tag("flatMapIterable");
        Flux.just("hello", "world").flatMapIterable(this::str2list).subscribe(this.cellConsumer(tag));


        // collectList 将多个元素转换为List
        tag = tag("collectList");
        Flux.just("hello", "world").collectList().subscribe(this.cellConsumer(tag));


        // collectMap 转换为Map输出
        tag = tag("collectMap");
        Flux.just("hello", "world").collectMap(s -> s.substring(0, 1)).subscribe(this.cellConsumer(tag));

        // 自定义的转换方式
        tag = tag("collect");
        Flux.just("hello", "world").collect(Collectors.joining("-")).subscribe(this.cellConsumer(tag));

        // reduce 将上个元素的reduce结果，与当前元素值作为参数，执行reduce方法，如下统计所有元素的字符串长度
        tag = tag("reduce");
        Flux.just("hello", "world").reduce(0, (ans, cell) -> ans + cell.length()).subscribe(this.cellConsumer(tag));

        // count 计数
        tag = tag("count");
        Flux.just("hello", "world").count().subscribe(this.cellConsumer(tag));
        System.out.println("\n");
    }


    private List<Character> str2list(String s) {
        List<Character> list = new ArrayList<>(s.length());
        for (char ch : s.toCharArray()) {
            list.add(ch);
        }
        return list;
    }


    private void filter() {
        System.out.println("========== filter() ===============");

        // 根据条件进行过滤, filterWhen 支持异步过滤 filter 同步过滤
        String tag = tag("filter");
        Flux.just("hello", "world", "a").filter(s -> s.length() > 2).subscribe(this.cellConsumer(tag));

        // 根据类型进行过滤
        tag = tag("ofType");
        Flux.just("hello", "world", 520).ofType(Integer.class).subscribe(this.cellConsumer(tag));

        // 去重
        tag = tag("distinct");
        Flux.just(120, 210, 120, 220).distinct().subscribe(this.cellConsumer(tag));

        // 去掉连续重复的数据
        tag = tag("distinctUntilChanged");
        Flux.just(1, 2, 2, 3, 2, 1).distinctUntilChanged().subscribe(this.cellConsumer(tag));
        System.out.println("\n");
    }

    private void pick() {
        System.out.println("========== pick() ===============");

        // 只取最后一个元素, 如果序列为空，会发出错误信号；
        String tag = tag("last");
        Flux.just(1, 2, 3).last().subscribe(this.cellConsumer(tag));
        // 获取最后一个元素，如果序列为空，返回默认值 2
        Flux.just().last(2).subscribe(this.cellConsumer(tag));

        // 获取最后几个元素
        tag = tag("takeLast");
        Flux.just(1, 2, 3).takeLast(2).subscribe(this.cellConsumer(tag));


        // 获取满足条件的元素及之前的元素，如下面输出 hello world data
        tag = tag("takeUntil");
        Flux.just("hello", "world", "dada", "yhh").takeUntil(s -> s.contains("a")).subscribe(this.cellConsumer(tag));

        // 返回满足条件的元素，直到第一个不满足的元素出现，当前及之后所有的元素都丢掉
        // 如果第一个就不满足，那么一个元素都不要
        tag = tag("takeWhile");
        Flux.just("dada", "haha", "yhh", "wa").takeWhile(s -> s.contains("a")).subscribe(this.cellConsumer(tag));
        Flux.just("hello", "haha", "yhh", "wa").takeWhile(s -> s.contains("a")).subscribe(this.cellConsumer(tag));

        // 获取指定序号的元素
        tag = tag("elementAt");
        Flux.just("hello", "world", "yhh").elementAt(2).subscribe(this.cellConsumer(tag));

        // 前面三个跳掉
        tag = tag("skip");
        Flux.just(1, 2, 3, 4, 5).skip(3).subscribe(this.cellConsumer(tag));

        // 跳过最后三个元素
        tag = tag("skipLast");
        Flux.just(1, 2, 3, 4, 5).skipLast(3).subscribe(this.cellConsumer(tag));

        // 从第一个元素开始，不满足条件则跳过，直到满足条件的元素出现，将当前元素及之后的，都拿出来
        // 如果第一个已经满足条件，那么所有元素都会被取出
        tag = tag("skipUntil");
        Flux.just(1, 2, 3, 4, 5, 1).skipUntil(s -> s <= 3).subscribe(this.cellConsumer(tag));
        tag = tag("skipUntil2");
        Flux.just(1, 2, 3, 4, 5, 1).skipUntil(s -> s >= 3).subscribe(this.cellConsumer(tag));

        // 从第一个元素开始，满足满足条件的都跳过，知道第一个不满足条件的出现，将当前元素及之后的，都拿出来
        // 如果第一个元素就不满足条件，那么所有的都会被取出
        tag = tag("skipWhile");
        Flux.just(1, 2, 3, 4, 5, 1).skipWhile(s -> s <= 3).subscribe(this.cellConsumer(tag));
        tag = tag("skipWhile2");
        Flux.just(1, 2, 3, 4, 5, 1).skipWhile(s -> s >= 3).subscribe(this.cellConsumer(tag));

        // 采样, 根据采样周期，获取数据
        tag = tag("sample");
        Flux.just(1, 2, 3, 4, 5).sample(Duration.ofMillis(10)).subscribe(this.cellConsumer(tag));
        System.out.println("\n");
    }

    private void insert() {
        System.out.println("========== insert() ===============");

        // 在开头添加新的元素
        String tag = tag("startWith");
        Flux.just("hello", "world").startWith("first").subscribe(this.cellConsumer(tag));

        // 在后面拼接元素
        tag = tag("endWith");
        Flux.just("hello", "world").concatWith(Mono.justOrEmpty("end")).subscribe(this.cellConsumer(tag));
    }


    private void action() {
        // 在不对序列做任何改变的情况下，做一些事情
        System.out.println("========== action() ===============");

        // 发出元素后回调
        String tag = tag("doOnNext");
        Flux.just(1, 2, 3).doOnNext(s -> System.out.println("doOnNext: " + s)).subscribe(this.cellConsumer(tag));

        // 完成之后回调
        tag = tag("doOnComplete");
        Flux.just(1, 2, 3).doOnComplete(() -> System.out.println("doOnComplete!!!"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // 错误时回调
        tag = tag("doOnSuccess");
        Flux.just(1, new RuntimeException("error"), 3)
                .doOnError((t) -> System.out.println("doOnError " + t.getMessage()))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // 取消时回调
        tag = tag("doOnCancel");
        Disposable disposable = Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1))
                .doOnCancel(() -> System.out.println("doOnCancel!"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
        disposable.dispose();

        // 结束时回调(包括正常完成，错误，取消）
        tag = tag("doFinally");
        Flux.just(1, new RuntimeException("error"), 3).doFinally((t) -> System.out.println("doOnError " + t))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // 订阅时回调
        tag = tag("doOnSubscribe");
        Flux.just(1, 2, 3).doOnSubscribe(s -> System.out.println("doOnSubscribe: " + s))
                .subscribe(this.cellConsumer(tag));


        // 所有类型的信号
        tag = tag("doOnEach");
        Flux.just(1, 2, 3).doOnEach(s -> System.out.println("doOnEach: " + s)).subscribe(this.cellConsumer(tag));


    }

}
