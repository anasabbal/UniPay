package com.unipay.service.user;


import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import com.unipay.repository.UserRepository;
import com.unipay.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User create(UserRegisterCommand command) {
        command.validate();
        log.info("Begin creating User with payload {}", JSONUtil.toJSON(command));
    }
}
