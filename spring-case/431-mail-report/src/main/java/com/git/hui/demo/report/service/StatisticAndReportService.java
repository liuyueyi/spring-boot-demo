package com.git.hui.demo.report.service;

import com.git.hui.demo.report.dao.UserStatisticMapper;
import com.git.hui.demo.report.dao.po.UserStatisticPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.List;

/**
 * @author YiHui
 * @date 2022/8/16
 */
@Service
public class StatisticAndReportService {
    @Autowired
    private UserStatisticMapper userStatisticMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private SpringTemplateEngine templateEngine;


    public StatisticVo statisticAddUserReport() {
        List<UserStatisticPo> list = userStatisticMapper.statisticUserCnt(30);
        StatisticVo vo = new StatisticVo();
        vo.setHtmlTitle("每日新增用户统计");
        vo.setTableTitle(String.format("【%s】新增用户报表", LocalDate.now()));
        vo.setList(list);
        return vo;
    }

    public String renderReport(StatisticVo vo) {
        Context context = new Context();
        context.setVariable("vo", vo);
        String content = templateEngine.process("report", context);
        return content;
    }


    // 定时发送，每天4:15分统计一次，发送邮件
//    @Scheduled(cron = "0 15 4 * * ?")
//    下上面这个是每分钟执行一次，用于本地测试
    @Scheduled(cron = "0/1 * * * * ?")
    public void autoCalculateUserStatisticAndSendEmail() throws MessagingException {
        StatisticVo vo = statisticAddUserReport();
        String content = renderReport(vo);
        sendMail("新增用户报告", content);
        System.out.println("over");
    }


    /**
     * 发送邮件的逻辑
     *
     * @param title
     * @param content
     * @throws MessagingException
     */
    public void sendMail(String title, String content) throws MessagingException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        //邮件发送人
        mimeMessageHelper.setFrom(environment.getProperty("spring.mail.from", "bangzewu@126.com"));
        //邮件接收人，可以是多个
        mimeMessageHelper.setTo("bangzewu@126.com");
        //邮件主题
        mimeMessageHelper.setSubject(title);
        //邮件内容
        mimeMessageHelper.setText(content, true);

        // 解决linux上发送邮件时，抛出异常 JavaMailSender no object DCH for MIME type multipart/mixed
        Thread.currentThread().setContextClassLoader(javax.mail.Message.class.getClassLoader());
        javaMailSender.send(mimeMailMessage);
    }

}
