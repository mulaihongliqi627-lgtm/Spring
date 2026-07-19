package com.amadeus.user.service;

public interface EmaiService {

    /**
     * 发送验证码
     * @param to 发件人
     * @param code 收件人
     * @return 是否发送成功
     */
    boolean sendVerificationCode(String to, String code);

    // 发送邮件（文件）到邮箱
    boolean sendEmailWithAttachment(
            String email,
            String subject,
            String content,
            String attachmentName,
            byte[] attachmentBytes,
            String attachmentType
    );
}
