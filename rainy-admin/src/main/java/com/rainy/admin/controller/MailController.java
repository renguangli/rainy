package com.rainy.admin.controller;

import com.rainy.common.Result;
import com.rainy.core.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * spring-boot-example-console
 *
 * @author renguangli
 * @date 2022/4/22 15:35
 */
@RestController
public class MailController {

    @Resource
    private MailService mailService;

    @PostMapping("/mail/text")
    public Result sendText(@RequestBody SimpleMailMessage simpleMailMessage){
        simpleMailMessage.setSentDate(new Date());
        mailService.sendText(simpleMailMessage);
        return Result.ok();
    }

}
