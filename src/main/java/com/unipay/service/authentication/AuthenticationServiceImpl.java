package com.unipay.service.authentication;


import com.unipay.annotation.Auditable;
import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import com.unipay.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserService userService;


    @Override
    @Auditable(action = "USER_REGISTRATION")
    public User register(UserRegisterCommand command, HttpServletRequest request) {
        return userService.create(command, request);
    }

    @Auditable(action = "USER_LOGIN")
    public String login() {
        // login logic
        return "NOT YEAT loooll";
    }
}
