package com.unipay.service.mail;

import com.unipay.models.User;

public interface EmailService {
    void confirmRegistration(String confirmationToken);
    void sendConfirmationEmail(User user);
}
