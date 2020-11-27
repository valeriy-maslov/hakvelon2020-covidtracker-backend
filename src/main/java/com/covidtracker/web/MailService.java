package com.covidtracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    public void sendMessageUsingThymeleafTemplate(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("Checklist.html", thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

    public void sendSimpleMessage() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hcspidergrasp@gmail.com");
        message.setTo("valeriy.maslov@akvelon.com");
        message.setSubject("Created");
        message.setText("testtest");
        emailSender.send(message);

    }

    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }

}
