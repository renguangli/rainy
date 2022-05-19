package com.rainy.core.service;

import org.springframework.mail.SimpleMailMessage;

/**
 * spring-boot-example-console
 *
 * @author renguangli
 * @date 2022/4/22 15:10
 */
public interface MailService {

    void sendText(SimpleMailMessage simpleMailMessage);
    void sendText(String to, String subject, String text);
    void sendHtml(String to, String subject, String text);
}
