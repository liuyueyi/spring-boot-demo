package com.git.hui.boot.email;

import com.git.hui.boot.email.demo.MailDemo;
import freemarker.template.TemplateException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author yihui
 * @date 2021/7/9
 */
@SpringBootApplication
public class Application {

    public Application(MailDemo mailDemo) throws MessagingException, IOException, TemplateException {
        mailDemo.basicSend();
        mailDemo.sendWithFile();
        mailDemo.freeMakerTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
