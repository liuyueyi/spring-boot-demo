package com.git.hui.boot.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * 管道拦截器，可以再握手、发送消息等个阶段进行回调
 *
 * @author YiHui
 * @date 2023/11/24
 */
@Slf4j
public class SocketOutChannelInterceptor implements ChannelInterceptor {
    /**
     * 再接收消息之前触发
     *
     * @param channel
     * @return
     */
    @Override
    public boolean preReceive(MessageChannel channel) {
        log.info("OUT: preReceive: {}", channel);
        return ChannelInterceptor.super.preReceive(channel);
    }

    /**
     * 接收消息之后触发
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.info("OUT: postReceive: {}", message);
        return ChannelInterceptor.super.postReceive(message, channel);
    }

    /**
     * 接收消息完成之后
     *
     * @param message
     * @param channel
     * @param ex
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        log.info("OUT: afterReceiveCompletion： {}", message);
        ChannelInterceptor.super.afterReceiveCompletion(message, channel, ex);
    }


    /**
     * 在消息被实际发送到频道之前调用
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("OUT: preSend: {}", message);
        return ChannelInterceptor.super.preSend(message, channel);
    }

    /**
     * 发送消息调用后立即调用
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.info("OUT: postSend: {}, sent:{}", message, sent);
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);//消息头访问器
        if (headerAccessor.getCommand() == null) return;// 避免非stomp消息类型，例如心跳检测

        String uname = (String) headerAccessor.getSessionAttributes().getOrDefault("uname", "-");
        switch (headerAccessor.getCommand()) {
            case CONNECT:
                log.info("[OUT] {} 开始了ws链接!", uname);
                break;
            case DISCONNECT:
                log.info("[OUT] {} 中断了ws链接", uname);
                break;
            case SUBSCRIBE:
                log.info("[OUT] {} 开始了订阅: {}", uname, message);
                break;
            case UNSUBSCRIBE:
                log.info("[OUT] {} 取消了订阅: {}", uname, message);
                break;
            default:
                break;
        }
        ChannelInterceptor.super.postSend(message, channel, sent);
    }


    /**
     * 在完成发送之后进行调用，不管是否有异常发生，一般用于资源清理
     *
     * @param message
     * @param channel
     * @param sent
     * @param ex
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        log.info("OUT: afterSendCompletion: {}, sent: {}", message, sent);
        ChannelInterceptor.super.afterSendCompletion(message, channel, sent, ex);
    }
}
