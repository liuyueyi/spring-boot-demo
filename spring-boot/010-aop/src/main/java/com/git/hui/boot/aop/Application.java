package com.git.hui.boot.aop;


import com.git.hui.boot.aop.demo.DemoBean;
import com.git.hui.boot.aop.demo2.AnoDemoBean;
import com.git.hui.boot.aop.demo2.ScopeDemoBean;
import com.git.hui.boot.aop.factory.ProxyFactoryDemoService;
import com.git.hui.boot.aop.order.InnerDemoBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 12:02 19/2/28.
 */
@SpringBootApplication
public class Application {

    private DemoBean demoBean;
    private AnoDemoBean anoDemoBean;

    private InnerDemoBean innerDemoBean;

    private ProxyFactoryDemoService proxyFactoryDemoService;

    public Application(DemoBean demoBean, AnoDemoBean anoDemoBean, InnerDemoBean innerDemoBean,
            ProxyFactoryDemoService proxyFactoryDemoService) {
        this.demoBean = demoBean;
        this.anoDemoBean = anoDemoBean;
        this.innerDemoBean = innerDemoBean;

        this.proxyFactoryDemoService = proxyFactoryDemoService;
        this.proxyFactoryDemoService.testShow();

        //        this.anoDemoBean();
        //        this.demoBean();

//        this.innerDemoBean();
    }


    private void innerDemoBean() {
        System.out.println("result: " + innerDemoBean.print());
    }

    private void anoDemoBean() {
        //        System.out.println(">>>>>>>" + anoDemoBean.randUUID(System.currentTimeMillis()));
        anoDemoBean.scopeUUID(System.currentTimeMillis());
    }

    private void demoBean() {
        System.out.println(">>>>> " + demoBean.genUUID(System.currentTimeMillis()));
    }


    //
    //    @SuppressWarnings("unchecked")
    //    public Application(Environment environment) {
    //        final String KEY = "alarm.title";
    //        System.out.println("old title ---> " + environment.getProperty(KEY));
    //
    //
    //        OriginTrackedValue value = (OriginTrackedValue) ((Map) ((StandardEnvironment) environment).getPropertySources()
    //                .get("applicationConfig: [classpath:/application.yml]").getSource()).get(KEY);
    //        OriginTrackedValue newVal = OriginTrackedValue.of("newTitle", value.getOrigin());
    //
    //        ((Map) ((StandardEnvironment) environment).getPropertySources()
    //                .get("applicationConfig: [classpath:/application.yml]").getSource()).put(KEY, newVal);
    //
    //        System.out.println("new title ----> " + environment.getProperty(KEY));
    //    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
