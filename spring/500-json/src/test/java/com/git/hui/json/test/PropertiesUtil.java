package com.git.hui.json.test;


import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * 资源文件转类
 *
 * @author yihui
 * @date 2021/8/26
 */
public class PropertiesUtil {

    /**
     * 从文件中读取配置
     *
     * @param propertyFile
     * @return
     * @throws IOException
     */
    public static Properties loadProperties(String propertyFile) throws IOException {
        Properties config = new Properties();
        config.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(propertyFile));
        return config;
    }

    private static boolean isPrimitive(Class clz) {
        if (clz.isPrimitive()) {
            return true;
        }

        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static <T> T toBean(Properties properties, Class<T> type, String prefix) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (prefix == null) {
            prefix = "";
        } else if (!prefix.isEmpty() && !prefix.endsWith(".")) {
            prefix += ".";
        }

        // 内省方式来初始化
        T obj = type.newInstance();
        BeanInfo bean = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = bean.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            // 只支持基本数据类型的拷贝
            Class fieldType = descriptor.getPropertyType();
            if (fieldType == Class.class) {
                continue;
            }

            if (isPrimitive(fieldType) || fieldType == String.class) {
                // 支持基本类型的转换，如果使用 PropertyUtils, 则不会实现基本类型 + String的自动转换
                BeanUtils.setProperty(obj, descriptor.getName(), properties.getProperty(prefix + descriptor.getName()));
            } else {
                BeanUtils.setProperty(obj, descriptor.getName(), toBean(properties, fieldType, prefix + descriptor.getName()));
            }
        }
        return obj;
    }

    @Data
    public static class MailProperties {
        private String host;
        private Integer port;
        private Smtp smtp;
        private String from;
        private String username;
        private String password;
    }

    @Data
    public static class Smtp {
        private String auth;
        private String starttlsEnable;
    }

    @Test
    public void testParse() throws Exception {
        Properties properties = loadProperties("mail.properties");
        MailProperties mailProperties = toBean(properties, MailProperties.class, "mail");
        System.out.println(mailProperties);
    }
}
