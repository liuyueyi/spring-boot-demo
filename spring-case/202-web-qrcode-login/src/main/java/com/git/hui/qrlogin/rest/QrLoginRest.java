package com.git.hui.qrlogin.rest;

import com.git.hui.qrlogin.util.IpUtils;
import com.github.hui.quick.plugin.base.DomUtil;
import com.github.hui.quick.plugin.base.constants.MediaType;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by @author yihui in 21:39 20/4/1.
 */
@CrossOrigin
@Controller
public class QrLoginRest {

    @Value(("${server.port}"))
    private int port;

    @GetMapping(path = "login")
    public String qr(Map<String, Object> data) throws IOException, WriterException {
        String id = UUID.randomUUID().toString();
        String ip = IpUtils.getLocalIP();

        String pref = "http://" + ip + ":" + port + "/";
        data.put("redirect", pref + "home");
        data.put("subscribe", pref + "subscribe?id=" + id);


        String qrUrl = pref + "scan?id=" + id;
        String qrCode = QrCodeGenWrapper.of(qrUrl).setW(200).setDrawPreColor(Color.RED)
                .setDrawStyle(QrCodeOptions.DrawStyle.CIRCLE).asString();
        data.put("qrcode", DomUtil.toDomSrc(qrCode, MediaType.ImageJpg));
        return "login";
    }


    private Map<String, SseEmitter> cache = new ConcurrentHashMap<>();

    @GetMapping(path = "subscribe", produces = {org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter subscribe(String id) {
        SseEmitter sseEmitter = new SseEmitter(5 * 60 * 1000L);
        cache.put(id, sseEmitter);
        sseEmitter.onTimeout(() -> cache.remove(id));
        sseEmitter.onError((e) -> cache.remove(id));
        return sseEmitter;
    }

    @GetMapping(path = "scan")
    public String scan(Model model, HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        SseEmitter sseEmitter = cache.get(request.getParameter("id"));
        if (sseEmitter != null) {
            sseEmitter.send("scan");
        }

        String url = "http://" + IpUtils.getLocalIP() + ":" + port + "/accept?id=" + id;
        model.addAttribute("url", url);
        return "scan";
    }

    @ResponseBody
    @GetMapping(path = "accept")
    public String accept(String id, String token) throws IOException {
        SseEmitter sseEmitter = cache.get(id);
        if (sseEmitter != null) {
            sseEmitter.send("login#qrlogin=" + token);
            sseEmitter.complete();
            cache.remove(id);
        }

        return "登录成功: " + token;
    }

    @GetMapping(path = {"home", ""})
    @ResponseBody
    public String home(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "未登录!";
        }

        Optional<Cookie> cookie = Stream.of(cookies).filter(s -> s.getName().equalsIgnoreCase("qrlogin")).findFirst();
        return cookie.map(cookie1 -> "欢迎进入首页: " + cookie1.getValue()).orElse("未登录!");
    }
}
