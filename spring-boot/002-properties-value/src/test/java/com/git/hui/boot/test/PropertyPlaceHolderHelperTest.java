package com.git.hui.boot.test;

import org.junit.Test;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/6/8
 */
public class PropertyPlaceHolderHelperTest {

    @Test
    public void testPropertyHolderHelper() {
        PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}", ":", true);
        String template = "hello ${demo.dd:123} world!";

        Map<String, String> map = new HashMap<>();
        map.put("demo.dd", "value");
        String ans = propertyPlaceholderHelper.replacePlaceholders(template, map::get);
        System.out.println(ans);
    }

}
