package com.unipay.service.authentication;


import com.unipay.annotation.Auditable;
import com.unipay.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{


    @Auditable(action = "USER_REGISTRATION")
    public UserDto register() {
        // login logic
        return null;
    }

    @Auditable(action = "USER_LOGIN")
    public String login() {
        // login logic
        return "NOT YEAT loooll";
    }
}
