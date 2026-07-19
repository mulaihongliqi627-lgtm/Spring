package com.amadeus.user.service.impl;

import com.amadeus.user.service.EmaiService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EmaiServiceImpl implements EmaiService {

    /**
     * Spring 邮件发送器
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送方邮箱
     */
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 发送邮件验证码的正文
     * @param code
     * @return
     */
    private String getContent(String code){
        return String.format("您的验证码是：%s\n\n"
                +"验证码有效期为5分钟,请及时使用",code);
    }

    @Override
    public boolean sendVerificationCode(String to, String code) {

        // 发送邮件的逻辑
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);

        String subject = "系统验证码";
        String content = getContent(code);

        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        log.info("[发送邮件] 验证码 {}  已发送至 {}", code, to);
        return true;
    }


    @Override
    public boolean sendEmailWithAttachment(String email, String subject, String
            content, String attachmentName, byte[] attachmentBytes, String attachmentType)
    {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF- 8");
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.addAttachment(attachmentName, new ByteArrayResource(attachmentBytes), attachmentType);
            mailSender.send(message);
            log.info("邮件已经发送⾄:{}，主题:{}, 附件:{}", email, subject, attachmentName);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
