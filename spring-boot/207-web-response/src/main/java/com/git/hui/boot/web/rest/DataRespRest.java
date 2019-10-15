package com.git.hui.boot.web.rest;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.web.entity.DemoRsp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 返回数据
 * Created by @author yihui in 15:27 19/9/13.
 */
@Controller
@RequestMapping(path = "data")
public class DataRespRest {

    /**
     * 直接返回字符串
     *
     * @return
     */
    @ResponseBody
    @GetMapping(path = "str")
    public String strRsp() {
        return "hello " + UUID.randomUUID().toString();
    }

    @ResponseBody
    @GetMapping(path = "str2")
    public void strRsp2(HttpServletResponse response) throws IOException {
        Map<String, String> ans = new HashMap<>(2);
        ans.put("a", "b");
        ans.put("b", "c");
        response.getOutputStream().write(JSON.toJSONString(ans).getBytes());
        response.getOutputStream().flush();
    }

    @ResponseBody
    @GetMapping(path = "html")
    public String strHtmlRsp() {
        return "<html>\n" + "<head>\n" + "    <title>返回数据测试</title>\n" + "</head>\n" + "<body>\n" +
                "<h1>欢迎欢迎，热烈欢迎</h1>\n" + "</body>\n" + "</html>";
    }

    /**
     * 返回POJO
     *
     * @return
     */
    @ResponseBody
    @GetMapping(path = "bean")
    public DemoRsp beanRsp() {
        return new DemoRsp(200, "success", UUID.randomUUID().toString() + "--->data");
    }


    /**
     * 返回数组
     *
     * @return
     */
    @ResponseBody
    @GetMapping(path = "ary")
    public String[] aryRsp() {
        return new String[]{UUID.randomUUID().toString(), LocalDateTime.now().toString()};
    }

    /**
     * 返回视图
     *
     * @return
     */
    @GetMapping(path = "view")
    public String viewRsp() {
        return "index";
    }


    /**
     * 返回图片
     */
    @GetMapping(path = "img")
    public void imgRsp(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        String path = "https://spring.hhui.top/spring-blog/imgs/info/info.png";
        URL uri = new URL(path);
        BufferedImage img = ImageIO.read(uri);
        ImageIO.write(img, "jpg", response.getOutputStream());
        System.out.println("--------");
    }
}
