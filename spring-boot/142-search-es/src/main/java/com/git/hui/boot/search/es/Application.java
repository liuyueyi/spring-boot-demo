package com.git.hui.boot.search.es;

import com.git.hui.boot.search.es.basic.BasicCurdDemo;
import com.git.hui.boot.search.es.basic.TermQueryDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 2022/1/7
 */
@SpringBootApplication
public class Application {

    public Application(EsTest esTest, BasicCurdDemo basicCurdDemo, TermQueryDemo termQueryDemo) throws Exception {
//        esTest.testGet();
//        basicCurdDemo.testOperate();
        termQueryDemo.testQuery();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
