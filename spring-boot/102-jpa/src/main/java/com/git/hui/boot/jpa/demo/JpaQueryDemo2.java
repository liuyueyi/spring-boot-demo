package com.git.hui.boot.jpa.demo;

import com.git.hui.boot.jpa.entity.MoneyPO;
import com.git.hui.boot.jpa.repository.MoneyBaseQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author yihui in 09:01 19/6/12.
 */
@Component
public class JpaQueryDemo2 {
    @Autowired
    private MoneyBaseQueryRepository moneyCurdRepository;

    public void queryTest() {

    }

    private void queryByGroup() {
        // 分组
    }

    private void queryMax() {
    }

}
