package com.git.hui.boot.web.rest;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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
