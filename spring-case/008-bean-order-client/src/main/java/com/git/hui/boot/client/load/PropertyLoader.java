package com.git.hui.boot.client.load;

import lombok.ToString;
import org.springframework.core.env.Environment;

/**
 * Created by @author yihui in 11:42 20/2/28.
 */
@ToString
public class PropertyLoader {

    private String specialValue;

    public PropertyLoader(Environment environment) {
        this.specialValue = environment.getProperty("speical.key");
        System.out.println("init propertyloader for:" + specialValue);
    }
}
