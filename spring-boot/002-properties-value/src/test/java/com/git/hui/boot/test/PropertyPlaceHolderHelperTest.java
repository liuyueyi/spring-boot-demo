package com.git.hui.boot.test;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import org.apache.commons.lang.text.ExtendedMessageFormat;
import org.junit.Test;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.util.PropertyPlaceholderHelper;

import java.text.MessageFormat;
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

        template = "hello ${demo.dd::} world!";
        ans = propertyPlaceholderHelper.replacePlaceholders(template, map::get);
        System.out.println(ans);

        template = "hello ${demo.dd:: world!";
        ans = propertyPlaceholderHelper.replacePlaceholders(template, map::get);
        System.out.println(ans);

        template = "hello ${demo.cc} world!";
        ans = propertyPlaceholderHelper.replacePlaceholders(template, map::get);
        System.out.println(ans);

        template = "hello ${demo.dd: ${demo.dd} world!";
        ans = propertyPlaceholderHelper.replacePlaceholders(template, map::get);
        System.out.println(ans);

        template = "hello ${demo.dd: ${demo.dd}} world!";
        ans = propertyPlaceholderHelper.replacePlaceholders(template, map::get);
        System.out.println(ans);
    }

    @Test
    public void testReplace() {
        // jdk 占位替换
        String msg = "{0}{1}{2}{3}{4}{5}{6}{7}{8}";
        Object[] array = new Object[]{"A", "B", "C", "D", "E", "F", "G", "H", "I",};
        String value = MessageFormat.format(msg, array);
        System.out.println(value);

        // 格式化字符串时，两个单引号才表示一个单引号，单个单引号会被省略，除非中文单引号不会被省略
        String out = MessageFormat.format("hello ''{0}''!", "hhui.top");
        System.out.println(out);

        // 单引号会使其后面的占位符均失效，导致直接输出占位符
        System.out.println(MessageFormat.format("hello '{0}", "hhui.top"));
    }

    @Test
    public void testReplaceV2() {
        // slf4j 日志占位替换
        FormattingTuple tupple = MessageFormatter.format("hello {}, there is something wrong", "world");
        System.out.println(tupple.getMessage());
    }
}
