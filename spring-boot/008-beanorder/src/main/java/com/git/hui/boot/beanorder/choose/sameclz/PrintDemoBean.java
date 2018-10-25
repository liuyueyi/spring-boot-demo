package com.git.hui.boot.beanorder.choose.sameclz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by @author yihui in 18:26 18/10/22.
 */
@Component
public class PrintDemoBean {

    @Resource(name = "logPrint")
    private IPrint print;

    /**
     * 如果下面的注解不指定name，则实例为logPrint
     */
    @Autowired
//    @Resource(name = "consolePrint")
    private IPrint consolePrint;

    @Autowired
    private IPrint logPrint;

    @Autowired(required = false)
    private IPrint xxxPrint;

    @PostConstruct
    public void init() {
        print.print("expect logPrint for [print]");
        consolePrint.print(" expect consolePrint for [consolePrint]");
        logPrint.print("expect logPrint for [logPrint]");
        xxxPrint.print("expect logPrint for [xxxPrint]");
    }
}
