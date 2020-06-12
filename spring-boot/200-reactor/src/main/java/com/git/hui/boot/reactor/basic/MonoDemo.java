package com.git.hui.boot.reactor.basic;

import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Mono 产生0-1个元素
 *
 * Created by @author yihui in 10:16 20/5/28.
 */
@Component
public class MonoDemo {
    private Consumer<Object> cellConsumer(String tag, long startTime) {
        return (o) -> {
            long cost = System.currentTimeMillis() - startTime;
            System.out.println(">consumer [" + tag + "] cost: " + cost);
        };
    }

    private Consumer<Object> cellConsumer(String tag) {
        return (o) -> System.out.println(">consumer [" + tag + "] ans: " + o);
    }

    private Consumer<Object> errorConsumer(String tag) {
        return (o) -> System.out.println(">error [" + tag + "] ans: " + o);
    }

    private Runnable onComplete(String tag) {
        return () -> System.out.println(">complete: " + tag);
    }


    public void basicTest() {
        create();

        operate();

        doXxx();

        emitDelay();

        then();
    }

    public void create() {
        // 创建一个不包含任何元素，只发布结束消息的序列
        String tag = "empty";
        System.out.println("========== empty() ===============");
        Mono.empty().subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // create方法，通过MonoSink来创建
        System.out.println("\n========== create() ===============");
        tag = "create";
        Mono.create((t) -> t.success("by create()"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // just 直接根据传参创建
        System.out.println("\n========== just() ===============");
        tag = "just";
        Mono.just("by just()").subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
        tag = "justOrEmpty";
        Mono.justOrEmpty(null).subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
        Mono.justOrEmpty(Optional.of("by justOrEmpty"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // 抛出异常, 异常会被触发
        tag = "error";
        System.out.println("\n========== error() ===============");
        Mono.error(new RuntimeException("exception1"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
        Mono.error(() -> new RuntimeException("exception2"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // never，不包含任何信息，异常结束都不会触发
        System.out.println("\n========== never() ===============");
        tag = "never";
        Mono.never().subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        this.fromCreate();
        this.delayCreate();
        this.deferCreate();
    }

    private void fromCreate() {
        System.out.println("\n========== fromXXX() ===============");
        String tag = "fromCallable";
        Mono.fromCallable(() -> "callable")
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
        // runnable任务，主要用于任务执行完之后的完成回调；或者异常回调
        tag = "fromRunnable";
        Mono.fromRunnable(() -> {
            System.out.println("do something in runnable!");
            throw new RuntimeException("run task error!");
        }).subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        tag = "fromSupplier";
        Mono.fromSupplier(() -> "supply")
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        tag = "fromFuture";
        Mono.fromFuture(CompletableFuture.completedFuture("future"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        tag = "fromCompletionStage";
        Disposable disposable = Mono.fromCompletionStage(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "fromCompletionStage first return";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "fromCompletionStage either return";
        }), (s) -> s)).subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
        while (!disposable.isDisposed()) {
            // 阻塞直到完成
        }

        // 由另外一个publisher产生数据
        tag = "fromDirect";
        Mono.fromDirect(Mono.just("data"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

    }

    /**
     * 延迟多久产生数据
     */
    private void delayCreate() {
        // delay
        System.out.println("\n========== delay() ===============");
        // 延迟1s，产生一个数字0
        String tag = "delay";
        long start = System.currentTimeMillis();
        Disposable disposable = Mono.delay(Duration.ofSeconds(1)).subscribe((s) -> {
            System.out.println("consumer " + tag + " return: " + s + " cost: " + (System.currentTimeMillis() - start));
        }, this.errorConsumer(tag), this.onComplete(tag));
        System.out.println("main thread cost: " + (System.currentTimeMillis() - start));
        while (!disposable.isDisposed()) {
            // 阻塞，直到消费完成
        }
    }


    /**
     * 懒汉式创建数据源，每次调用subscribe时，创建对象；而just则属于恶汉式，第一次创建对象，后续的subscribe时，数据不变
     */
    private void deferCreate() {
        System.out.println("\n========== defer() ===============");
        Mono<Date> direct = Mono.just(new Date());
        Mono<Date> defer = Mono.defer(() -> Mono.just(new Date()));
        direct.subscribe((s) -> System.out.println("just1: " + s));
        defer.subscribe((s) -> System.out.println("defer1: " + s));

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        direct.subscribe((s) -> System.out.println("just2: " + s));
        defer.subscribe((s) -> System.out.println("defer2: " + s));
    }


    private void doXxx() {
        System.out.println("\n========== doXxx() ===============");

        String tag = "doFirst";
        // 消费之前触发
        Mono.just("hello world").doFirst(() -> System.out.println("second")).doFirst(() -> System.out.println("first"))
                .doFirst(() -> System.out.println("zero")).subscribe(this.cellConsumer(tag));


        // 成功发出元素之后，会触发doOnNext中的方法
        tag = "doOnNext";
        Mono.just("hello world").doOnNext((s) -> System.out.println("onNext: " + s)).subscribe(this.cellConsumer(tag));


        // 正确完成之后，触发
        tag = "doOnSuccess";
        Mono.just("hello world").doOnSuccess((s) -> System.out.println("onSuccess: " + s))
                .subscribe(this.cellConsumer(tag));
        Mono.error(new RuntimeException("error")).doOnSuccess((s) -> System.out.println("onSuccess: " + s))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag));


        // 因为异常结束的回调
        tag = "doOnError";
        Mono.error(new RuntimeException("error")).doOnError((s) -> System.out.println("onError: " + s))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag));


        // 取消时，回调
        tag = "doOnCancel";
        Disposable disposable = Mono.delay(Duration.ofSeconds(1)).thenReturn("hello world")
                .doOnCancel(() -> System.out.println("onCancel!"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        disposable.dispose();


        // 所有结束的情况，都会触发（包括正常完成，错误，取消）
        tag = "doFinally";
        Mono.just("hello world").doFinally((s) -> System.out.println("finally: " + s))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // 记录日志
        tag = "log";
        Mono.just("hello world").log(tag + "日志").subscribe(this.cellConsumer(tag));
    }


    private void emitDelay() {
        System.out.println("\n========== delay() ===============");

        String tag = "delayElement";
        long start = System.currentTimeMillis();
        String finalTag = tag;
        long finalStart = start;
        // doOnNext 中接收到数据会延迟1s
        Disposable disposable = Mono.just("hello world").
                doOnNext((s) -> {
                    System.out.println(finalTag + " element parse cost: " + (System.currentTimeMillis() - finalStart));
                }).delayElement(Duration.ofSeconds(1)).subscribe(this.cellConsumer(tag, start));
        while (!disposable.isDisposed()) {
        }

        start = System.currentTimeMillis();
        tag = "delaySubscription";
        String finalTag1 = tag;
        long finalStart1 = start;
        disposable = Mono.just("hello world").doOnNext((s) -> {
            System.out.println(finalTag1 + " element parse cost: " + (System.currentTimeMillis() - finalStart1));
        }).delaySubscription(Duration.ofSeconds(1)).subscribe(this.cellConsumer(tag, start));
        while (!disposable.isDisposed()) {
        }

        start = System.currentTimeMillis();
        tag = "delayUntil";
        String finalTag2 = tag;
        long finalStart2 = start;
        disposable = Mono.just("hello world").doOnNext((s) -> {
            System.out.println(finalTag2 + " element parse cost: " + (System.currentTimeMillis() - finalStart2));
        }).delayUntil(s -> Mono.delay(Duration.ofSeconds(1))).subscribe(this.cellConsumer(tag, start));
        while (!disposable.isDisposed()) {
        }
    }


    public void operate() {
        System.out.println("\n>>>>>>>>>> operate <<<<<<<<<<<<<");
        operateMap();
        operateFilter();
        operateZip();
        operateTake();
        operateConcat();
    }

    private void operateMap() {
        System.out.println("\n========== map() ===============");
        // map 直接操作数据，返回数据
        String tag = "map";
        Mono.just("hello world").map(String::length)
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // flatMap 也是操作数据，但是返回的是Mono/Flux，转换操作中可以一步操作
        long start = System.currentTimeMillis();
        Disposable disposable = Mono.just("hello world").flatMap(s -> Mono.delay(Duration.ofSeconds(1)).thenReturn(s))
                .subscribe(this.cellConsumer(tag, start));
        while (!disposable.isDisposed()) {
        }

        tag = "flatMapIterable";
        Mono.just("hello world").flatMapIterable(s -> Arrays.asList(s.split(" ")))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));


        // 类型转换
        tag = "cast";
        Mono.just("12").cast(Integer.class).subscribe(this.cellConsumer(tag));
    }

    private void operateFilter() {
        System.out.println("\n========== filter() ===============");
        // 过滤，满足条件的元素可以投递给消费者
        String tag = "filter";
        Mono.just("hello world").filter(s -> s.length() > 5)
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
        Mono.just("hello world").filter(s -> s.length() > 35)
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));


        // 根据类型进行判断
        Mono.just("hello world").ofType(String.class)
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // 忽略所有的元素
        tag = "ignoreElement";
        Mono.just("hello world").ignoreElement()
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));
    }


    private void operateTake() {
        System.out.println("\n========== take() ===============");
        String tag = "take";
        // 延迟1s之后，取数据
        long start = System.currentTimeMillis();
        Disposable disposable = Mono.just("hello world").take(Duration.ofSeconds(1))
                .subscribe(this.cellConsumer(tag, start), this.errorConsumer(tag), this.onComplete(tag));
        while (!disposable.isDisposed()) {
        }


        tag = "takeUntilOther";
        start = System.currentTimeMillis();
        disposable = Mono.just("hello world").takeUntilOther(Mono.delay(Duration.ofSeconds(1)).thenReturn("Welcome "))
                .subscribe(this.cellConsumer(tag, start), this.errorConsumer(tag), this.onComplete(tag));
        while (!disposable.isDisposed()) {
        }
    }

    private void operateZip() {
        System.out.println("\n========== zip() ===============");
        // zip, 将多个Mono的元素，合成一个n元组 TupleN 对象
        String tag = "zip";
        Mono.zip(Mono.just("hello"), Mono.just("world"))
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // zipWith 第二个参数，可以将两个mono的元素，按照某个规则计算，输出一个值
        tag = "zipWith";
        Mono.just(520).zipWith(Mono.just("hello world"), (a, b) -> b + " -> " + a)
                .subscribe(this.cellConsumer(tag), this.errorConsumer(tag), this.onComplete(tag));

        // zipWhen 当前mono的元素，参与参数中Mono的构建，然后返回二元组
        tag = "zipWhen";
        Mono.just("hello world").zipWhen((s) -> Mono.justOrEmpty(s + "123")).subscribe(this.cellConsumer(tag));
    }

    private void operateConcat() {
        System.out.println("\n========== concat() ===============");
        String tag = "concatWith";
        // 合并，成一个Flux, 当前Mono作为第一个元素
        Disposable disposable =
                Mono.just("hello world").delayElement(Duration.ofSeconds(1)).concatWith(Flux.just("一灰灰", "二灰灰"))
                        .subscribe(this.cellConsumer(tag));
        while (!disposable.isDisposed()) {
        }

        // 合并，与上面的主要区别是，Mono中的元素不定是合并后Flux中的第一个元素
        tag = "mergeWith";
        disposable = Mono.just("hello world").delayElement(Duration.ofSeconds(1)).mergeWith(Flux.just("一灰灰", "二灰灰"))
                .subscribe(this.cellConsumer(tag));
        while (!disposable.isDisposed()) {
        }
    }


    private void then() {
        // 有一个序列，但是我对序列的元素值不感兴趣：ignoreElements
        //...并且我希望用 Mono 来表示序列已经结束：then
        //...并且我想在序列结束后等待另一个任务完成：thenEmpty
        //...并且我想在序列结束之后返回一个 Mono：Mono#then(mono)
        //...并且我想在序列结束之后返回一个值：Mono#thenReturn(T)
        //...并且我想在序列结束之后返回一个 Flux：thenMany
        System.out.println("\n========== then() ===============");
        String tag = "thenReturn";
        Mono.just("hello world").ignoreElement().thenReturn("new content").subscribe(this.cellConsumer(tag));
    }
}