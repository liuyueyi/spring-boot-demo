package com.git.hui.boot.web.interceptor.server;

import com.git.hui.boot.web.interceptor.inter.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 2021/11/18
 */
@Component
public class BasicDemo {
    @Autowired
    private TestDemo testDemo;

    public String process(String data) {
        return Data.process(data);
    }

    private String show() {
        return show2();
    }

    public String show2() {
        return testDemo.showCase();
    }

    String test() {
        return testDemo.testCase();
    }

    public enum Data {
        SHOW("show") {
            @Override
            String doProcess() {
                BasicDemo basicDemo = SpringUtil.getBean(BasicDemo.class);
                String an = basicDemo.show();
                return an;
            }
        },
        CASE("test") {
            @Override
            String doProcess() {
                BasicDemo basicDemo = SpringUtil.getBean(BasicDemo.class);
                String an = basicDemo.test();
                return an;
            }
        };

        private String data;

        Data(String data) {
            this.data = data;
        }

        abstract String doProcess();

        static String process(String data) {
            for (Data d : values()) {
                if (d.data.equalsIgnoreCase(data)) {
                    return d.doProcess();
                }
            }
            return null;
        }
    }
}
