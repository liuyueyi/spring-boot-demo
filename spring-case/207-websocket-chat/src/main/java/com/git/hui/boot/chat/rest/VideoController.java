package com.git.hui.boot.chat.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.websocket.Session;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YiHui
 * @date 2023/12/12
 */
@Slf4j
@Controller
public class VideoController {
    @MessageMapping("video/{target}")
    public void videoChat(String message, @DestinationVariable("target") String target, SimpMessageHeaderAccessor headerAccessor) {
        log.info("接收到请求参数: {}", message);
        try {
            //jackson
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            //JSON字符串转 HashMap
            HashMap hashMap = mapper.readValue(message, HashMap.class);

            //消息类型
            String type = (String) hashMap.get("type");

            //to user
            String toUser = (String) hashMap.get("toUser");
            String fromUser = (String) hashMap.get("fromUser");

            //msg
            String msg = (String) hashMap.get("msg");

            //sdp
            String sdp = (String) hashMap.get("sdp");

            //ice
            Map iceCandidate = (Map) hashMap.get("iceCandidate");

            HashMap<String, Object> map = new HashMap<>();
            map.put("type", type);

            //对方挂断
            if ("hangup".equals(type)) {
                map.put("fromUser", fromUser);
                map.put("msg", "对方挂断！");
            }

            //视频通话请求
            if ("call_start".equals(type)) {
                map.put("fromUser", fromUser);
                map.put("msg", "1");
            }

            //视频通话请求回应
            if ("call_back".equals(type)) {
                map.put("fromUser", toUser);
                map.put("msg", msg);
            }

            //offer
            if ("offer".equals(type)) {
                map.put("fromUser", toUser);
                map.put("sdp", sdp);
            }

            //answer
            if ("answer".equals(type)) {
                map.put("fromUser", toUser);
                map.put("sdp", sdp);
            }

            //ice
            if ("_ice".equals(type)) {
                map.put("fromUser", toUser);
                map.put("iceCandidate", iceCandidate);
            }

            WsAnswerHelper.publish(target, "/topic/video", mapper.writeValueAsString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
