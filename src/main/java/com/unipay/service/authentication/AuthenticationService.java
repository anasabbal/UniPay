package com.unipay.service.authentication;

import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    User register(UserRegisterCommand command, HttpServletRequest request);
}
