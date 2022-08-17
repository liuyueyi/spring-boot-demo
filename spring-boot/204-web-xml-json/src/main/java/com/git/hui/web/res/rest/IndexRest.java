package com.git.hui.web.res.rest;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YiHui
 * @date 2022/8/17
 */
@RestController
public class IndexRest {

    @Data
    public static class ResVo<T> {
        private int code;
        private String msg;
        private T data;

        public ResVo(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }
    }

    /**
     * 浏览器直接访问时，返回的 Content-Type: application/xhtml+xml
     * 使用curl访问时，返回的 Content-Type: application/json
     * - curl http://127.0.0.1:8080 -i -v
     * 可以通过请求头 accept: application/json, 或者 accept: application/xml 来指定返回json还是html
     * - curl http://127.0.0.1:8080 -H 'Accept: application/xml' -iv
     *
     * @return
     */
    @GetMapping(path = "/")
    public ResVo<String> index() {
        return new ResVo<>(0, "ok", "简单的测试");
    }

    @GetMapping(path = "/xml", produces = {MediaType.APPLICATION_XML_VALUE})
    public ResVo<String> xml() {
        return new ResVo<>(0, "ok", "返回xml");
    }

    @GetMapping(path = "/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResVo<String> json() {
        return new ResVo<>(0, "ok", "返回json");
    }


    @GetMapping(path = "param")
    public ResVo<String> params(@RequestParam(name = "mediaType", required = false) String mediaType) {
        return new ResVo<>(0, "ok", String.format("基于传参来决定返回类型：%s", mediaType));
    }


    /**
     * 若ContentNegotiationConfigurer中的defaultContentType属性没有配置，则返回的类型的优先级是 xml > json， 根据定义顺序来的
     * 如果默认值有定义，则 defaultContentType 中定义的优先级更高，json > xml
     * @param mediaType
     * @return
     */
    @GetMapping(path = "p2", produces = {"application/xml", "application/json"})
    public ResVo<String> params2(@RequestParam(name = "mediaType", required = false) String mediaType) {
        return new ResVo<>(0, "ok", String.format("基于传参来决定返回类型：%s", mediaType));
    }
}
