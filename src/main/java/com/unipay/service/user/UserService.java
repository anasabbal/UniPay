package com.unipay.service.user;

import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;

public interface UserService {
    User create(final UserRegisterCommand command);
}
