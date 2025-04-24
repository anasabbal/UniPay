package com.unipay.settings;

import com.unipay.command.UserSettingsCommand;
import com.unipay.models.User;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserSettingsRepository;
import com.unipay.service.settings.UserSettingsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSettingsServiceImplTest {

    @Mock
    private UserSettingsRepository userSettingsRepository;

    @InjectMocks
    private UserSettingsServiceImpl userSettingsService;

    @Test
    void create_shouldValidateAndPersistSettings() {
        UserSettingsCommand command = mock(UserSettingsCommand.class);
        UserSettings settings = mock(UserSettings.class);
        User user = mock(User.class);

        try (MockedStatic<UserSettings> staticMock = mockStatic(UserSettings.class)) {
            staticMock.when(() -> UserSettings.create(command)).thenReturn(settings);
            when(userSettingsRepository.save(settings)).thenReturn(settings);

            userSettingsService.create(command, user);

            verify(command).validate();
            verify(settings).setUser(user);
            verify(userSettingsRepository).save(settings);
        }
    }
}
