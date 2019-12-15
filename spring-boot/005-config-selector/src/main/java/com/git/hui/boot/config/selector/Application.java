package com.git.hui.boot.config.selector;

import com.git.hui.boot.config.selector.ordercase.ano.DemoSelector;
import com.git.hui.boot.config.selector.printcase.PrintSelector;
import com.git.hui.boot.config.selector.printcase.config.PrintConfigSelector;
import com.git.hui.boot.config.selector.printcase.print.IPrint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 18:14 19/12/13.
 */
@PrintSelector(PrintConfigSelector.FileConfiguration .class)
//@PrintSelector(PrintConfigSelector.DbConfiguration .class)
//@PrintSelector
@DemoSelector
@SpringBootApplication
public class Application {

    public Application(IPrint print) {
        print.print();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
