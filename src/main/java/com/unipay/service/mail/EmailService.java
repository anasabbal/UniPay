package com.unipay.service.mail;

import com.unipay.models.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void confirmRegistration(String confirmationToken);
    void sendConfirmationEmail(User user);
}
