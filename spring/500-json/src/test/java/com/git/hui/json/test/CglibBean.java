package com.git.hui.json.test;

import org.mockito.internal.util.reflection.Fields;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yihui
 * @date 2021/8/19
 */
public class CglibBean {
    private Object object = null;
    private BeanMap beanMap = null;

    public CglibBean(Map<String, Object> map) {
        this.object = instanceObject(map);
        initObject(map);
    }

    private Object instanceObject(Map<String, Object> map) {
        Map<String, Class> properties = new HashMap<>(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            properties.put(entry.getKey(), entry.getValue().getClass());
        }
        // 根据属性生成实体bean
        return generateBean(properties);
    }

    private void initObject(Map<String, Object> map) {
        //用实体Bean创建BeanMap，用于获取和设置value
        beanMap = BeanMap.create(this.object);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            beanMap.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 得到该实体bean对象
     *
     * @return
     */
    public Object getObject() {
        return this.object;
    }

    public Object getValue(String key) {
        return beanMap.get(key);
    }

    private Object generateBean(Map<String, Class> propertyMap) {
        //根据一组属性名和属性值的类型，动态创建Bean对象
        BeanGenerator generator = new BeanGenerator();
        Set keySet = propertyMap.keySet();
        for (Iterator i = keySet.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            generator.addProperty(key, (Class) propertyMap.get(key));
        }
        return generator.create();  //创建Bean
    }


    public static void main(String[] args) throws IllegalAccessException { // 设置类成员属性
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 123);
        map.put("name", "hello");
        map.put("now", new Date());

        CglibBean bean = new CglibBean(map);
        // 获得bean的实体
        Object object = bean.getObject();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f: fields) {
            f.setAccessible(true);
            System.out.println("field: " + f.getName() + " = " + f.get(object));
        }

        System.out.println("----------------");
        System.out.println(bean.getValue("id"));
    }
}
