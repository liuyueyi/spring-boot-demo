package com.git.hui.demo.prometheus.metric;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.search.Search;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 监控相关
 *
 * @author yihui
 * @date 2021/4/15
 */
public class MetricWrapper {
    @Setter
    private static MeterRegistry meterRegistry;

    /**
     * 计数，主要用于
     */
    public static Builder create() {
        return new Builder(meterRegistry);
    }


    public static class Builder {
        private MeterRegistry meterRegistry;
        private String key;
        private List<Tag> tags;
        private Duration min;
        private Duration max;
        private List<Duration> slos;

        public Builder(MeterRegistry meterRegistry) {
            this.meterRegistry = meterRegistry;
            tags = new ArrayList<>();
            slos = new ArrayList<>();
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder tags(List<Tag> tags) {
            this.tags.addAll(tags);
            return this;
        }

        public Builder tag(Tag tag) {
            tags.add(tag);
            return this;
        }

        public Builder min(Duration min) {
            this.min = min;
            return this;
        }

        public Builder min(Long mill) {
            return min(Duration.ofMillis(mill));
        }

        public Builder max(Duration max) {
            this.max = max;
            return this;
        }

        public Builder max(Long mill) {
            return max(Duration.ofMillis(mill));
        }

        public Builder slots(List<Duration> slos) {
            this.slos.addAll(slos);
            return this;
        }

        public Builder slots(Duration slos) {
            this.slos.add(slos);
            return this;
        }

        public Builder slots(Long slos) {
            return this.slots(Duration.ofMillis(slos));
        }

        private Object countLock = new Object();
        private Object gaugeLock = new Object();
        private Object histogramLock = new Object();

        /**
         * 计数组件,可以用来统计访问量
         *
         * @return
         */
        public Counter count() {
            Counter ct = find(Search::counter);
            return checkAndInit(ct, countLock, () -> meterRegistry.counter(key, tags));
        }

        /**
         * 聚合组件
         *
         * @return
         */
        public Timer histogram() {
            Timer timer = find(Search::timer);
            return checkAndInit(timer, histogramLock, () -> Timer.builder(key).minimumExpectedValue(min).maximumExpectedValue(max)
                    .sla(slos.isEmpty() ? null : slos.toArray(new Duration[]{})).tags(tags)
                    .register(meterRegistry));
        }

        private <T> T find(Function<Search, T> func) {
            if (tags == null) {
                return func.apply(meterRegistry.find(key));
            } else {
                return func.apply(meterRegistry.find(key).tags(tags));
            }
        }

        private <T> T checkAndInit(T target, Object lock, Supplier<T> func) {
            if (target != null) {
                return target;
            }

            synchronized (lock) {
                if (target == null) {
                    System.out.println("init metric: " + func.toString());
                    return func.get();
                }
            }
            return target;
        }
    }
}
