package com.unipay.service.mail;


import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.ConfirmationToken;
import com.unipay.models.User;
import com.unipay.repository.ConfirmationTokenRepository;
import com.unipay.repository.UserRepository;
import com.unipay.utils.JSONUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService{

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;


    @Value("${app.name}")
    private String appName;

    @Value("${app.support.email}")
    private String supportEmail;

    @Value("${app.website}")
    private String websiteUrl;


    @Async
    @Override
    @Transactional
    public void sendConfirmationEmail(User user) {
        try {
            ConfirmationToken token = ConfirmationToken.create(user);
            confirmationTokenRepository.save(token);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Confirm Your " + appName + " Account");
            helper.setText(buildEmailContent(user, token), true);

            mailSender.send(message);
            log.info("Confirmation email sent to {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send confirmation email", e);
            throw new BusinessException(ExceptionPayloadFactory.FAILED_TO_SEND_EMAIL.get());
        }
    }

    @Override
    public void confirmRegistration(String confirmationCode) {
        log.info("Begin verifying confirmation code");
        ConfirmationToken token = findConfirmationTokenByToken(confirmationCode);
        if (token != null) {
            String email = token.getUser().getEmail();
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())
            );
            log.info("Email {} verified successfully!", email);
        } else {
            log.warn("Verification failed: Invalid confirmation code");
        }
    }
    private ConfirmationToken findConfirmationTokenByToken(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.CONFIRMATION_TOKEN_NOT_FOUND.get()));
        if (token != null) {
            log.info("Confirmation Token with payload {} fetched successfully!", JSONUtil.toJSON(token));
        }
        return token;
    }
    private String buildEmailContent(User user, ConfirmationToken token) {
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<!DOCTYPE html>")
                .append("<html lang=\"en\">")
                .append("<head>")
                .append("    <meta charset=\"UTF-8\">")
                .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                .append("    <title>Email Verification</title>")
                .append("</head>")
                .append("<body style=\"margin: 0; padding: 0; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\">")
                .append("    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
                .append("        <tr>")
                .append("            <td align=\"center\" style=\"padding: 40px 0;\">")
                .append("                <table width=\"600\" style=\"border: 1px solid #eaeaea; border-radius: 8px; padding: 40px;\">");

        // Header with your provided image for the UniPay logo
        emailContent.append("                    <tr>")
                .append("                        <td align=\"center\">")
                .append("                            <img src=\"https://i.ibb.co/gFvQfqpt/Chat-GPT-Image-Apr-29-2025-02-55-42-PM.png\" alt=\"")
                .append(appName).append(" Logo\" width=\"120\" style=\"margin-bottom: 24px;\">")
                .append("                            <h1 style=\"color: #1a1a1a; margin: 0 0 24px 0;\">Verify Your Email</h1>")
                .append("                        </td>")
                .append("                    </tr>");

        // Main body with user's name and confirmation code
        emailContent.append("                    <tr>")
                .append("                        <td style=\"padding: 24px 0; border-top: 1px solid #eaeaea;\">")
                .append("                            <p style=\"color: #666666; margin: 0 0 24px 0; line-height: 1.6;\">")
                .append("                                Hi ").append(user.getUsername()).append(",<br><br>")
                .append("                                Thank you for creating an account with ").append(appName).append(". Please use the following verification code to confirm your email address:")
                .append("                            </p>")
                .append("                            <div style=\"background: #f8f9fa; padding: 16px; border-radius: 6px; text-align: center; margin: 24px 0;\">")
                .append("                                <code style=\"font-size: 24px; letter-spacing: 2px; color: #2d3748; font-weight: bold;\">")
                .append(token.getConfirmationToken())
                .append("                                </code>")
                .append("                            </div>")
                .append("                            <p style=\"color: #666666; margin: 24px 0; line-height: 1.6;\">")
                .append("                                This code will expire in 24 hours. If you didn't request this code, you can safely ignore this email.")
                .append("                            </p>")
                .append("                        </td>")
                .append("                    </tr>");

        // Footer with support contact and copyright information
        emailContent.append("                    <tr>")
                .append("                        <td style=\"padding-top: 24px; border-top: 1px solid #eaeaea;\">")
                .append("                            <p style=\"color: #999999; font-size: 12px; line-height: 1.6; margin: 0;\">")
                .append("                                Need help? Contact our support team at <a href=\"mailto:")
                .append(supportEmail).append("\" style=\"color: #4299e1; text-decoration: none;\">")
                .append(supportEmail).append("</a><br>")
                .append("                                © ").append(LocalDate.now().getYear()).append(" ").append(appName).append(". All rights reserved.")
                .append("                            </p>")
                .append("                        </td>")
                .append("                    </tr>");

        // Closing tags for the HTML email
        emailContent.append("                </table>")
                .append("            </td>")
                .append("        </tr>")
                .append("    </table>")
                .append("</body>")
                .append("</html>");

        return emailContent.toString();
    }
}
