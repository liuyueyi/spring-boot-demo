package com.git.hui.boot.web.xml.rest;

import com.git.hui.boot.web.xml.rest.model.WxTxtMsgReqVo;
import com.git.hui.boot.web.xml.rest.model.WxTxtMsgResVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 以微信公众号的回调为例
 *
 * @author
 * @date 2022/7/4
 */
@RestController
public class XmlRest {

    /**
     * curl -X POST 'http://localhost:8080/xml/callback' -H 'content-type:application/xml' -d '<xml><URL><![CDATA[https://hhui.top]]></URL><ToUserName><![CDATA[一灰灰blog]]></ToUserName><FromUserName><![CDATA[123]]></FromUserName><CreateTime>1655700579</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[测试]]></Content><MsgId>11111111</MsgId></xml>' -i
     *
     * @param msg
     * @param request
     * @return
     */
    @PostMapping(path = "xml/callback",
            consumes = {"application/xml", "text/xml"},
            produces = "application/xml;charset=utf-8")
    public WxTxtMsgResVo callBack(@RequestBody WxTxtMsgReqVo msg, HttpServletRequest request) {
        WxTxtMsgResVo res = new WxTxtMsgResVo();
        res.setFromUserName(msg.getToUserName());
        res.setToUserName(msg.getFromUserName());
        res.setCreateTime(System.currentTimeMillis() / 1000);
        res.setMsgType("text");
        res.setContent("hello: " + LocalDateTime.now());
        return res;
    }

    /**
     * curl -X POST 'http://localhost:8080/json/callback' -H 'content-type:application/json' -d '{"toUserName":"一灰灰blog", "fromUserName": "test", "createTime": 1655700579, "updateTime":1655700579,"msgType": "text", "content": "简单的测试", "msgId": 1231}'
     *
     * @param msg
     * @param request
     * @return
     */
    @PostMapping(path = "json/callback", consumes = {"application/json"}, produces = "application/json")
    public WxTxtMsgResVo json(@RequestBody WxTxtMsgResVo msg, HttpServletRequest request) {
        WxTxtMsgResVo res = new WxTxtMsgResVo();
        res.setFromUserName(msg.getToUserName());
        res.setToUserName(msg.getFromUserName());
        res.setCreateTime(System.currentTimeMillis() / 1000);
        res.setMsgType("text");
        res.setContent("hello: " + LocalDateTime.now());
        return res;
    }

    /**
     * curl -X POST 'http://localhost:8080/text/callback' -H 'content-type:application/json' -d '{"toUserName":"一灰灰blog", "fromUserName": "test", "createTime": 1655700579, "updateTime":1655700579,"msgType": "text", "content": "简单的测试", "msgId": 1231}'
     *
     * @param msg
     * @param request
     * @return
     */
    @PostMapping(path = "text/callback")
    public WxTxtMsgResVo text(@RequestBody WxTxtMsgResVo msg, HttpServletRequest request) {
        WxTxtMsgResVo res = new WxTxtMsgResVo();
        res.setFromUserName(msg.getToUserName());
        res.setToUserName(msg.getFromUserName());
        res.setCreateTime(System.currentTimeMillis() / 1000);
        res.setMsgType("text");
        res.setContent("hello: " + LocalDateTime.now());
        return res;
    }
}
