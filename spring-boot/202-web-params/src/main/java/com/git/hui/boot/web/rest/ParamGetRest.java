package com.git.hui.boot.web.rest;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.web.resolver.ListParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Get请求参数的处理方式
 *
 * Created by @author yihui in 14:08 19/3/29.
 */
@RestController
@RequestMapping(path = "get")
public class ParamGetRest {

    /**
     * 具体传入的请求参数，通过 {@link ServletRequest#getParameter(java.lang.String)} 获取
     *
     * - http://localhost:8080/get/req?name=%E4%B8%80%E7%81%B0%E7%81%B0blog&age=10&id=1,2,3
     *
     * @param httpRequest
     * @return
     */
    @GetMapping(path = "req")
    public String requestParam(HttpServletRequest httpRequest) {
        Map<String, String[]> ans = httpRequest.getParameterMap();
        return JSON.toJSONString(ans);
    }

    @GetMapping(path = "req2")
    public String requestParam2() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String name = request.getParameter("name");
        return "param Name=" + name;
    }

    //  -------------------------------------------------------------------------------------------


    /**
     * 通过方法参数接受http请求参数，要求方法的参数签名和请求参数名一致
     *
     * - 传入两个参数, 正好对上:  http://127.0.0.1:8080/get/arg?name=一灰灰blog&age=18
     * - 传入一个参数:  http://127.0.0.1:8080/get/arg?name=一灰灰blog
     * - 不传参数： http://127.0.0.1:8080/get/arg
     * - 传入一个对不上的参数: http://127.0.0.1:8080/get/arg?name=一灰灰blog&id=110
     *
     * @param name
     * @param age
     * @return
     */
    @GetMapping(path = "arg")
    public String argParam(String name, Integer age) {
        String ans = "name: " + name + " age: " + age;
        System.out.println(ans);
        return ans;
    }


    /**
     * 如果请求发送的参数为数组时，可以直接用数组进行接收，注意不能使用List
     *
     * http://127.0.0.1:8080/get/arg2?names=一灰灰blog,小灰灰blog&size=2
     *
     * @param names
     * @param size
     * @return
     */
    @GetMapping(path = "arg2")
    public String argParam2(String[] names, int size) {
        return "name: " + (names != null ? Arrays.asList(names) : "null") + " size: " + size;
    }

    //  -------------------------------------------------------------------------------------------


    /**
     * 方法参数中指定 @RequestParam 注解来讲方法参数与请求参数进行关联映射
     *
     * - http://localhost:8080/get/ano?name=一灰灰blog&age=18&uids=1,3,4
     * - http://localhost:8080/get/ano?name=一灰灰blog&uids=1,3,4
     * - http://localhost:8080/get/ano?name=一灰灰blog
     * - http://localhost:8080/get/ano?uids=1,3,4
     *
     * @param uname
     * @param age
     * @return
     */
    @GetMapping(path = "ano")
    public String anoParam(@RequestParam(name = "name") String uname,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "uids", required = false) Integer[] uids) {
        return "name: " + uname + " age: " + age + " uids: " + (uids != null ? Arrays.asList(uids) : "null");
    }

    /**
     * 混合使用的case :
     * - http://localhost:8080/get/ano2?name=一灰灰blog&age=18
     *
     * @param names
     * @return
     */
    @GetMapping(path = "ano1")
    public String anoParam1(@RequestParam(name = "names") List<String> names) {
        return "name: " + names;
    }

    @GetMapping(path = "arg3")
    public String anoParam2(List<String> names) {
        return "names: " + names;
    }

    public enum TYPE {
        A, B, C;
    }

    @GetMapping(path = "enum")
    public String enumParam(TYPE type) {
        return type.name();
    }

    @GetMapping(path = "enum2")
    public String enumParam2(@RequestParam TYPE type) {
        return type.name();
    }

    /**
     * map获取请求参数
     *
     * - http://127.0.0.1:8080/get/mapper?name=yihuihui&age=18&uIds=1,3,4
     *
     * @param params
     * @return
     */
    @GetMapping(path = "mapper")
    public String mapperParam(@RequestParam Map<String, Object> params) {
        return params.toString();
    }


    @GetMapping(path = "mapper2")
    public String mapperParam2(Map<String, Object> params) {
        return params.toString();
    }

    //  -------------------------------------------------------------------------------------------


    /**
     * url 请求参数方式，下面给出几个测试case，看下会怎样
     *
     * - http://127.0.0.1:8080/get/url/yihhuihui/1
     * - http://127.0.0.1:8080/get/url/yihhuihui
     * - http://127.0.0.1:8080/get/url/yihhuihui/1/test
     *
     * @param name
     * @param index
     * @return
     */
    @GetMapping(path = "url/{name}/{index}")
    public String urlParam(@PathVariable(name = "name") String name,
            @PathVariable(name = "index", required = false) Integer index) {
        return "name: " + name + " index: " + index;
    }


    //  -------------------------------------------------------------------------------------------


    /**
     * POJO方式获取请求参数
     *
     * - http://127.0.0.1:8080/get/bean?name=yihuihui&age=18&uIds=1,3,4
     * - http://127.0.0.1:8080/get/bean?name=yihuihui&age=18
     *
     * @param req
     * @return
     */
    @GetMapping(path = "bean")
    public String beanParam(BaseReqDO req) {
        return req.toString();
    }


    // -------------------------------------------------------------------------------------------

    /**
     * 自定义参数解析器
     *
     * @param names
     * @param age
     * @return
     */
    @GetMapping(path = "self")
    public String selfParam(@ListParam(name = "names") List<String> names, Integer age) {
        return names + " | age=" + age;
    }

    @GetMapping(path = "self2")
    public String selfParam2(List<String> names, Integer age) {
        return names + " | age=" + age;
    }
}