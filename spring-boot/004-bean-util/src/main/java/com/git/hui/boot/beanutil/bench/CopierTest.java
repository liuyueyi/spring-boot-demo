package com.git.hui.boot.beanutil.bench;

import com.git.hui.boot.beanutil.copier.ApacheCopier;
import com.git.hui.boot.beanutil.copier.CglibCopier;
import com.git.hui.boot.beanutil.copier.HutoolCopier;
import com.git.hui.boot.beanutil.copier.SpringBeanCopier;
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
 * @author wuzebang
 * @date 2021/4/7
 */
@Component
public class CopierTest {
    @Autowired
    private ApacheCopier apacheCopier;
    @Autowired
    private CglibCopier cglibCopier;
    @Autowired
    private HutoolCopier hutoolCopier;
    @Autowired
    private SpringBeanCopier springBeanCopier;

    private Random random = new Random();

    public Source genSource() {
        Source source = new Source();
        source.setId(random.nextInt());
        source.setIds(Arrays.asList(random.nextLong(), random.nextLong(), random.nextLong()));
        source.setMarket(new BigDecimal(random.nextFloat()));
        source.setPrice(random.nextInt(120) / 10.0d);
        source.setUser_name("一灰灰Blog");
        return source;
    }

    public void test() throws Exception {
        preLoad(Target.class);
        autoCheck(Target.class);
    }

    private <T> void preLoad(Class<T> target) throws Exception {
        for (int i = 0; i < 100; i++) {
            Source s = genSource();
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("apache");
            T ta = apacheCopier.copy(s, target);
            stopWatch.stop();
            stopWatch.start("cglib");
            T tc = cglibCopier.copy(s, target);
            stopWatch.stop();
            stopWatch.start("hutool");
            T th = hutoolCopier.copy(s, target);
            stopWatch.stop();
            stopWatch.start("spring");
            T ts = springBeanCopier.copy(s, target);
            stopWatch.stop();
            System.out.println(s + "\n" + ta + "\n" + tc + "\n" + th + "\n" + ts);
            System.out.println("-------------------- cost: " + stopWatch.prettyPrint() + " ---------------------------");
        }
    }

    private <T> void autoCheck(Class<T> target) throws Exception {
        int size = 10_0000;
        StopWatch stopWatch = new StopWatch();

        stopWatch.start("apacheCopier");
        for (int i = 0; i < size; i++) {
            Source s = genSource();
            apacheCopier.copy(s, target);
        }
        stopWatch.stop();


        stopWatch.start("cglibCopier");
        for (int i = 0; i < size; i++) {
            Source s = genSource();
            cglibCopier.copy(s, target);
        }
        stopWatch.stop();


        stopWatch.start("hutoolCopier");
        for (int i = 0; i < size; i++) {
            Source s = genSource();
            hutoolCopier.copy(s, target);
        }
        stopWatch.stop();

        stopWatch.start("springBeanCopier");
        for (int i = 0; i < size; i++) {
            Source s = genSource();
            springBeanCopier.copy(s, target);
        }
        stopWatch.stop();
        System.out.println("-------------------- cost: " + stopWatch.prettyPrint() + " ---------------------------");
    }
}
