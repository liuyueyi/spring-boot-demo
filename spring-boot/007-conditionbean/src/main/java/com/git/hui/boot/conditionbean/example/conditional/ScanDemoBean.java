package com.git.hui.boot.conditionbean.example.conditional;

import com.git.hui.boot.conditionbean.example.conditional.condition.ScanDemoCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 14:34 18/10/18.
 */
@Component
@Conditional(ScanDemoCondition.class)
public class ScanDemoBean {

    @Value("${conditional.demo.load}")
    private boolean load;

    public boolean getLoad() {
        return load;
    }
}
