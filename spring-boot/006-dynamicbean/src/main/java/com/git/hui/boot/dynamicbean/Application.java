package com.git.hui.boot.dynamicbean;

import com.git.hui.boot.dynamicbean.auto.AutoFacDIBean;
import com.git.hui.boot.dynamicbean.bean.auto.AnoAutoOriginBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Created by @author yihui in 09:43 18/10/13.
 */
@SpringBootApplication
public class Application {

    public Application(AutoFacDIBean autoFacDIBean, AnoAutoOriginBean anoAutoOriginBean) {
        String str = autoFacDIBean.print();
        System.out.println(str);

        String m = anoAutoOriginBean.print();
        System.out.println(m);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
