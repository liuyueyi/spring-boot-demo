package com.git.hui.boot.web.rest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * post请求，搭配博文查看 <a href="https://spring.hhui.top/spring-blog/2019/08/28/190828-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8Bweb%E7%AF%87%E4%B9%8BPost%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0%E8%A7%A3%E6%9E%90%E5%A7%BF%E5%8A%BF%E6%B1%87%E6%80%BB/"/>
 * Created by @author yihui in 18:48 19/8/23.
 */
@RestController
@RequestMapping(path = "post")
public class ParamPostRest {

    /**
     * RequestEntity 方式，不支持Content-type: 'application/x-www-form-urlencoded;charset=UTF-8'
     *
     * curl 'http://127.0.0.1:8080/post/entity' -X POST -H 'content-type:application/json;charset:UTF-8' -d '{"name": "yihui", "age": 20}'
     *
     * @param requestEntity
     * @return
     */
    @PostMapping(path = "entity")
    public String entityParam(RequestEntity requestEntity) {
        return Objects.requireNonNull(requestEntity.getBody()).toString();
    }

    /**
     * json串
     *
     * curl 'http://127.0.0.1:8080/post/body' -X POST -H 'content-type:application/json;charset:utf-8' -d '{"name": "yihui", "age": 20}'
     *
     * @param req
     * @return
     */
    @PostMapping(path = "body")
    public String bodyParam(@RequestBody BaseReqDO req) {
        return req == null ? "null" : req.toString();
    }

    /**
     * 最基本的使用方式
     *
     * curl 'http://127.0.0.1:8080/post/req' -X POST -H 'content-type:application/json;charset:utf-8' -d '{"name": "yihui", "age": 20}'
     * curl 'http://127.0.0.1:8080/post/req' -X POST -d 'name=yihui&age=18'
     *
     * @param req
     * @return
     */
    @PostMapping(path = "req")
    public String requestParam(HttpServletRequest req) {
        System.out.println(req.getParameter("name"));
        return JSONObject.toJSONString(req.getParameterMap());
    }

    private String getMsg(MultipartFile file) {
        String ans = null;
        try {
            ans = file.getName() + " = " + new String(file.getBytes(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        System.out.println(ans);
        return ans;
    }

    /**
     * 文件上传
     *
     * curl 'http://127.0.0.1:8080/post/file' -X POST -F 'file=@hello.txt'
     *
     * @param file
     * @return
     */
    @PostMapping(path = "file")
    public String fileParam(@RequestParam("file") MultipartFile file) {
        return getMsg(file);
    }

    @PostMapping(path = "file2")
    public String fileParam2(MultipartHttpServletRequest request) {
        MultipartFile file = request.getFile("file");
        return getMsg(file);
    }
}
