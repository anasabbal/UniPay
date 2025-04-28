package com.unipay.service.mail;


import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.ConfirmationToken;
import com.unipay.models.User;
import com.unipay.repository.ConfirmationTokenRepository;
import com.unipay.repository.UserRepository;
import com.unipay.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService{

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Async
    @Override
    @Transactional
    public void sendEmail(SimpleMailMessage email){
        javaMailSender.send(email);
    }

    @Override
    public void confirmRegistration(String confirmationToken) {
        log.info("Begin finding confirmation token by token ");
        ConfirmationToken token = findConfirmationTokenByToken(confirmationToken);
        if (token != null) {
            log.info("Begin fetching client with email");
            String email = token.getUser().getEmail();
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())
            );
            if (user != null) {
                log.info("Email verified successfully!");
                return;
            }
        }
        log.info("Error: Couldn't verify email");
    }
    private ConfirmationToken findConfirmationTokenByToken(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.CONFIRMATION_TOKEN_NOT_FOUND.get()));
        if (token != null) {
            log.info("Confirmation Token with payload {} fetched successfully!", JSONUtil.toJSON(token));
        }
        return token;
    }
}
