package com.git.hui.boot.autoinject.example;

import com.git.hui.boot.autoinject.ano.AutoInject;
import org.springframework.stereotype.Service;

/**
 * @author yihui
 * @date 2021/2/9
 */
@Service
public class RestService {
    @AutoInject
    private DemoService demoService;

    private DemoService2 demoService2;

    @AutoInject
    public void setDemoService2(DemoService2 demoService2) {
        this.demoService2 = demoService2;
    }

    public void test() {
        int ans = demoService.calculate(10, 20);
        System.out.println(ans);

        ans = demoService2.calculate(11, 22);
        System.out.println(ans);
    }
}
