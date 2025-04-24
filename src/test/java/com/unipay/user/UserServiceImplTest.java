package com.unipay.user;


import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import com.unipay.repository.UserRepository;
import com.unipay.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldValidateAndLogUserCreation() {
        UserRegisterCommand command = mock(UserRegisterCommand.class);
        User user = User.create(command);

        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.create(command);

        verify(command).validate();
    }
}
