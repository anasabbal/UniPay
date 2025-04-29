package com.unipay.service.authentication;

import com.unipay.command.LoginCommand;
import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import com.unipay.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    User register(UserRegisterCommand command, HttpServletRequest request);
    LoginResponse login(LoginCommand command, HttpServletRequest request);
}
