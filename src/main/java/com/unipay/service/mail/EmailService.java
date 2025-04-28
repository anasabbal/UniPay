package com.unipay.service.mail;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(SimpleMailMessage email);
    void confirmRegistration(String confirmationToken);
}
