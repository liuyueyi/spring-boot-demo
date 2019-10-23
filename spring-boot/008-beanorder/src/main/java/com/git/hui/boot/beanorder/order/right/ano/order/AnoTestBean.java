package com.git.hui.boot.beanorder.order.right.ano.order;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by @author yihui in 11:00 19/10/23.
 */
@Component
public class AnoTestBean {

    public AnoTestBean(List<IBean> anoBeanList) {
        for (IBean bean : anoBeanList) {
            System.out.println("in ano testBean: " + bean.getClass().getName());
        }
    }
}
