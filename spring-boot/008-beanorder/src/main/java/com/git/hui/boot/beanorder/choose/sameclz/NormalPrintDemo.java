//package com.git.hui.boot.beanorder.choose.sameclz;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
///**
// * Created by @author yihui in 19:28 18/10/22.
// */
//@Component
//public class NormalPrintDemo {
//    @Resource(name = "consolePrint")
//    private IPrint consolePrint;
//
//    @Autowired
//    private IPrint logPrint;
//
//    @PostConstruct
//    public void init() {
//        consolePrint.print(" console print!!!");
//        logPrint.print(" log print!!!");
//    }
//}
