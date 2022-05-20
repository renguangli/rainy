package com.rainy.core.service.impl;

import com.rainy.common.CharConstants;
import com.rainy.common.ConfigConstants;
import com.rainy.core.service.ConfigService;
import com.rainy.core.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * rainy-console
 *
 * @author renguangli
 * @date 2022/4/22 15:10
 */
@Slf4j
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Resource
    private ConfigService configService;

    @Async
    @Override
    public void asyncSendText(String to, String subject, String text) {
        this.sendText(to, subject, text);
    }

    @Override
    public void sendText(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(this.getFrom());
        mailMessage.setTo(to);
        mailMessage.setSentDate(new Date());
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        this.sendText(mailMessage);
    }

    @Override
    public void sendText(SimpleMailMessage simpleMailMessage) {
        JavaMailSender mailSender = getMailSender();
        mailSender.send(simpleMailMessage);
    }

    @Async
    @Override
    public void asyncSendHtml(String to, String subject, String text) {
        this.sendHtml(to, subject, text);
    }

    @Override
    public void sendHtml(String to, String subject, String text) {
        try {
            JavaMailSender mailSender = this.getMailSender();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(getFrom());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送失败：{}", e.getMessage(), e);
        }
    }

    private JavaMailSender getMailSender(){
        String mailConfig = configService.get(ConfigConstants.MAIL_CONFIG);
        String[] mailConfigs = mailConfig.split(CharConstants.COLON);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfigs[0]);
        mailSender.setPort(Integer.parseInt(mailConfigs[1]));
        mailSender.setUsername(mailConfigs[2]);
        mailSender.setPassword(mailConfigs[3]);
        return mailSender;
    }

    private String getFrom(){
        String mailConfig = configService.get(ConfigConstants.MAIL_CONFIG);
        String[] mainConfigs = mailConfig.split(CharConstants.COLON);
        return mainConfigs[2];
    }

}
