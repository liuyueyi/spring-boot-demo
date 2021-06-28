package com.git.hui.boot.beanutil.bench;

import com.git.hui.boot.beanutil.copier.*;
import com.git.hui.boot.beanutil.model.Source;
import com.git.hui.boot.beanutil.model.Target;
import com.git.hui.boot.beanutil.model.Target2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

/**
 * @author yihui
 * @date 2021/4/7
 */
@Component
public class CopierTest {
    @Autowired
    private ApacheCopier apacheCopier;
    @Autowired
    private SpringCglibCopier springCglibCopier;
    @Autowired
    private PureCglibCopier pureCglibCopier;
    @Autowired
    private HutoolCopier hutoolCopier;
    @Autowired
    private SpringBeanCopier springBeanCopier;
    @Autowired
    private MapsCopier mapsCopier;
    @Autowired
    private OrikaCopier orikaCopier;

    private Random random = new Random();

    public Source genSource() {
        Source source = new Source();
        source.setId(random.nextInt());
        source.setIds(Arrays.asList(random.nextLong(), random.nextLong(), random.nextLong()));
        source.setMarketPrice(new BigDecimal(random.nextFloat()));
        source.setPrice(random.nextInt(120) / 10.0d);
        source.setUser_name("一灰灰Blog");
        return source;
    }

    public void test() throws Exception {
        copyTest();
        camelParse();
        // 第一次用于预热
        autoCheck(Target.class, 10000);
        autoCheck(Target.class, 10000);
        autoCheck(Target.class, 10000_0);
        autoCheck(Target.class, 50000_0);
//        autoCheck(Target.class, 10000_00);

//        autoCheck2(Target2.class, 10000);
//        autoCheck2(Target2.class, 10000);
//        autoCheck2(Target2.class, 10000_0);
//        autoCheck2(Target2.class, 50000_0);
//        camelParse();
    }

    private void copyTest() throws Exception {
        Source s = genSource();
        Target ta = apacheCopier.copy(s, Target.class);
        Target ts = springBeanCopier.copy(s, Target.class);
        Target tc = springCglibCopier.copy(s, Target.class);
        Target tp = pureCglibCopier.copy(s, Target.class);
        Target th = hutoolCopier.copy(s, Target.class);
        Target tm = mapsCopier.copy(s, Target.class);
        Target to = orikaCopier.copy(s, Target.class);
        System.out.println("source:\t" + s + "\napache:\t" + ta + "\nspring:\t" + ts
                + "\nsCglib:\t" + tc + "\npCglib:\t" + tp + "\nhuTool:\t" + th + "\nmapStruct:\t" + tm + "\norika:\t" + to);
    }

    private <T> void autoCheck(Class<T> target, int size) throws Exception {
        StopWatch stopWatch = new StopWatch();
        runCopier(stopWatch, "apacheCopier", size, (s) -> apacheCopier.copy(s, target));
        runCopier(stopWatch, "springCglibCopier", size, (s) -> springCglibCopier.copy(s, target));
        runCopier(stopWatch, "pureCglibCopier", size, (s) -> pureCglibCopier.copy(s, target));
        runCopier(stopWatch, "hutoolCopier", size, (s) -> hutoolCopier.copy(s, target));
        runCopier(stopWatch, "springBeanCopier", size, (s) -> springBeanCopier.copy(s, target));
        runCopier(stopWatch, "mapStruct", size, (s) -> mapsCopier.copy(s, target));
        runCopier(stopWatch, "orikaCopier", size, (s) -> orikaCopier.copy(s, target));
        System.out.println((size / 10000) + "w -------- cost: " + stopWatch.prettyPrint());
    }

    private <T> void autoCheck2(Class<T> target, int size) throws Exception {
        StopWatch stopWatch = new StopWatch();
        runCopier(stopWatch, "apacheCopier", size, (s) -> apacheCopier.copy(s, target));
        runCopier(stopWatch, "springCglibCopier", size, (s) -> springCglibCopier.copyAndParse(s, target));
        runCopier(stopWatch, "pureCglibCopier", size, (s) -> pureCglibCopier.copyAndParse(s, target));
        runCopier(stopWatch, "hutoolCopier", size, (s) -> hutoolCopier.copyAndParse(s, target));
        runCopier(stopWatch, "springBeanCopier", size, (s) -> springBeanCopier.copy(s, target));
        runCopier(stopWatch, "mapStruct", size, (s) -> mapsCopier.copyAndParse(s, target));
        runCopier(stopWatch, "mapStruct", size, (s) -> orikaCopier.copy(s, target));
        System.out.println((size / 10000) + "w -------- cost: " + stopWatch.prettyPrint());
    }

    private <T> void runCopier(StopWatch stopWatch, String key, int size, CopierFunc func) throws Exception {
        stopWatch.start(key);
        for (int i = 0; i < size; i++) {
            Source s = genSource();
            func.apply(s);
        }
        stopWatch.stop();
    }

    @FunctionalInterface
    public interface CopierFunc<T> {
        T apply(Source s) throws Exception;
    }

    private void camelParse() throws Exception {
        Source s = genSource();
        Target2 cglib = springCglibCopier.copyAndParse(s, Target2.class);
        Target2 cglib2 = pureCglibCopier.copyAndParse(s, Target2.class);
        Target2 hutool = hutoolCopier.copyAndParse(s, Target2.class);
        Target2 map = mapsCopier.copyAndParse(s, Target2.class);
        Target2 orika = orikaCopier.copy(s, Target2.class);
        System.out.println("source:" + s + "\nsCglib:" + cglib + "\npCglib:" + cglib2 + "\nhuTool:" + hutool + "\nMapStruct:" + map + "\norika:" + orika);
    }
}
