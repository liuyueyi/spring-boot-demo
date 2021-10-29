package com.git.hui.spring.json.stream;

import com.git.hui.spring.json.bean.SimpleBean;
import com.google.gson.stream.JsonWriter;
import org.testng.annotations.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 流式序列化/反序列化的使用姿势
 *
 * @author yihui
 * @date 2021/10/29
 */
public class GsonStreamDemo {


    @Test
    public void testStream() throws Exception {
        SimpleBean simpleBean = new SimpleBean().setUserId(12).setUserName("yh").setUserMoney(12.3D).setUserSkills(Arrays.asList("1", "2", "3"))
                .setExtra(new HashMap<String, Object>(8) {
                    private static final long serialVersionUID = 893460816623439571L;

                    {
                        put("a", "123");
                        put("b", 345);
                        put("c", Arrays.asList("1", "2", "3"));
                        put("d", 35.1f);
                    }
                });

        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(System.out));

        BeanInfo bean = Introspector.getBeanInfo(SimpleBean.class);
        jsonWriter.beginObject();
        for (PropertyDescriptor property: bean.getPropertyDescriptors()) {
            Object val = property.getReadMethod().invoke(simpleBean);
            jsonWriter.name(property.getName()).value(String.valueOf(val));
        }
        jsonWriter.endObject();
        jsonWriter.close();
    }

}
