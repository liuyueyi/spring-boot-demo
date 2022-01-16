package com.git.hui.boot.web.rest;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * xml参数解析，建议搭配博文查看: <a href="https://spring.hhui.top/spring-blog/2020/07/06/200706-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8Bxml%E4%BC%A0%E5%8F%82%E4%B8%8E%E8%BF%94%E5%9B%9E%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/"/>
 *
 * Created by @author yihui in 11:54 20/7/3.
 */
@RestController
@RequestMapping(path = "xml")
public class XmlParamsRest {

    @JacksonXmlRootElement(localName = "req")
    @Data
    public static class XmlBean {
        private String name;

        private Integer age;
    }

    @Data
    @JacksonXmlRootElement(localName = "res")
    public static class XmlRes {
        private String msg;

        private Integer code;

        private String data;
    }

    @PostMapping(path = "show", consumes = {MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_XML_VALUE)
    public XmlRes show(@RequestBody XmlBean bean) {
        System.out.println(bean);
        XmlRes res = new XmlRes();
        res.setCode(0);
        res.setMsg("success");
        res.setData(bean.toString());
        return res;
    }
}
