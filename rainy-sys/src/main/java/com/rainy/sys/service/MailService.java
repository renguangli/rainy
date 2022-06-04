package com.rainy.sys.service;

import org.springframework.mail.SimpleMailMessage;

/**
 * rainy-console
 *
 * @author renguangli
 * @date 2022/4/22 15:10
 */
public interface MailService {

    void asyncSendText(String to, String subject, String text);
    void sendText(String to, String subject, String text);
    void sendText(SimpleMailMessage simpleMailMessage);

    void asyncSendHtml(String to, String subject, String text);
    void sendHtml(String to, String subject, String text);
}
