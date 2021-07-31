package com.git.hui.demo.mail.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author yihui
 * @date 21/7/31
 */
public class MailUtil extends AppenderBase<ILoggingEvent> {

    public static void sendMail(String title, String context) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件发送人
        simpleMailMessage.setFrom(ContextUtil.getEnvironment().getProperty("spring.mail.from", "bangzewu@126.com"));
        //邮件接收人，可以是多个
        simpleMailMessage.setTo("bangzewu@126.com");
        //邮件主题
        simpleMailMessage.setSubject(title);
        //邮件内容
        simpleMailMessage.setText(context);

        JavaMailSender javaMailSender = ContextUtil.getApplicationContext().getBean(JavaMailSender.class);
        javaMailSender.send(simpleMailMessage);
    }

    private static final long INTERVAL = 10 * 1000 * 60;
    private long lastAlarmTime = 0;

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        if (canAlarm()) {
            sendMail(iLoggingEvent.getLoggerName(), iLoggingEvent.getFormattedMessage());
        }
    }

    private boolean canAlarm() {
        long now = System.currentTimeMillis();
        if (now - lastAlarmTime >= INTERVAL) {
            lastAlarmTime = now;
            return true;
        } else {
            return false;
        }
    }
}
