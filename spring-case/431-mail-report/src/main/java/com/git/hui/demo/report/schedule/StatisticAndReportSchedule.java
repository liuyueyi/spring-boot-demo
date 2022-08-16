package com.git.hui.demo.report.schedule;

import com.git.hui.demo.report.dao.UserStatisticMapper;
import com.git.hui.demo.report.dao.po.UserStatisticPo;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YiHui
 * @date 2022/8/16
 */
@Service
public class StatisticAndReportSchedule {
    @Autowired
    private UserStatisticMapper userStatisticMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    private String EMAIL_TEMPLATE;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @PostConstruct
    public void init() {
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("report.html")) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                List<String> lines = reader.lines().collect(Collectors.toList());
                EMAIL_TEMPLATE = Joiner.on("\n").join(lines);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 15 4 * * ?")
    public void loadStatisticInfo() {
        List<UserStatisticPo> list = userStatisticMapper.statisticUserCnt(30);
        Context context = new Context();
        context.setVariable("title", "每日新增用户邮件通知");
        context.setVariable("cntList", list);
        context.setVariable("tableTitle", "新增用户统计（" + LocalDate.now() + "）");
        sendMail("新增用户报告", context);
    }


    public void sendMail(String title, Context context) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件发送人
        simpleMailMessage.setFrom(environment.getProperty("spring.mail.from", "bangzewu@126.com"));
        //邮件接收人，可以是多个
        simpleMailMessage.setTo("bangzewu@126.com");
        //邮件主题
        simpleMailMessage.setSubject(title);
        // 模板渲染
        String content = templateEngine.process(EMAIL_TEMPLATE, context);
        //邮件内容
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }

}
