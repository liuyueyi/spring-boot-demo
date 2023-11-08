package com.git.hui.boot.web.rest;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 返回结果
 *
 * @author YiHui
 * @date 2023/11/6
 */
@Controller
public class RspController {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class UserInfo {
        private String name;
        private Integer code;
    }

    private List<UserInfo> allUsers(int size) {
        List<UserInfo> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new UserInfo(UUID.randomUUID() + "_用户", i));
        }
        return list;
    }

    /**
     * 对象方式返回, 用于模拟不管返回的内容多小，都会进行gzip压缩
     *
     * @return
     */
    @ResponseBody
    @GetMapping(path = "list")
    public List<UserInfo> list(@RequestParam(name = "size", defaultValue = "128", required = false) Integer size) {
        List<UserInfo> list = allUsers(size);
        return list.subList(0, size);
    }

    /**
     * 字符串方式返回, 会根据设置的最小长度，来确定是否会对返回结果进行gzip压缩
     *
     * @return
     */
    @ResponseBody
    @GetMapping(path = "strList")
    public String strList(@RequestParam(name = "size", defaultValue = "128", required = false) Integer size) {
        List<UserInfo> list = allUsers(size);
        return JSON.toJSONString(list);
    }


    /**
     * 模拟返回文件的case
     *
     * @param size
     * @return
     */
    @ResponseBody
    @GetMapping(path = "file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] fileResp(int size) {
        List<UserInfo> list = allUsers(size);
        String ans = JSON.toJSONString(list);
        return ans.getBytes();
    }


    @Data
    @JsonFormat(with = {JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES})
    public static class TT {
        String pOrder;
        String SOrderNum;
        Long c2Num;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TT t = new TT();
        t.setPOrder("1230981217834143");
        t.setSOrderNum("1230981212334143");
        t.setC2Num(12L);
        String str = mapper.writeValueAsString(t);
        TT t2 = mapper.readValue(str, TT.class);
        System.out.println(t2);



//        下面这个主要用于解决gzip解压
//        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> ans = restTemplate.getForEntity("http://localhost:8080/strList?size=1", String.class);
        System.out.println(ans);

        HttpEntity<ArrayList> ans2 = restTemplate.getForEntity("http://localhost:8080/list?size=1", ArrayList.class);
        System.out.println(ans2);

        HttpEntity<ArrayList> a3 = restTemplate.getForEntity("http://localhost:8080/static/txt.json", ArrayList.class);
        System.out.println(a3);


//        以下时解决京东开放接口的数据，返回中文乱码情况

        HttpHeaders DEFAULT_HEAD = new HttpHeaders();
//        DEFAULT_HEAD.set("Accept", "*/*");
        // 支持gzip压缩传输
        // gzip 压缩，默认的RestTemplate不支持，需要借助 HttpComponentsClientHttpRequestFactory
        // deflate zlib格式压缩, Brotli压缩 默认的RestTemplate都可以
        DEFAULT_HEAD.set("Accept-Encoding", "deflate,br,gzip");
        DEFAULT_HEAD.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
        DEFAULT_HEAD.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //封装请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(DEFAULT_HEAD);

        HttpEntity<String> ans3 = restTemplate.exchange("https://api-iop.jd.com/oauth2/accessToken", HttpMethod.POST, formEntity, String.class);
        System.out.println(ans3);

        DEFAULT_HEAD.set("Accept", "*/*");
        formEntity = new HttpEntity<>(DEFAULT_HEAD);
        HttpEntity<String> ans4 = restTemplate.exchange("https://api-iop.jd.com/oauth2/accessToken", HttpMethod.POST, formEntity, String.class);
        System.out.println(ans4);
    }
}
