package com.unipay.user;

import com.unipay.command.UserRegisterCommand;
import com.unipay.command.ProfileCommand;
import com.unipay.command.UserSettingsCommand;
import com.unipay.models.*;
import com.unipay.repository.UserRepository;
import com.unipay.service.profile.UserProfileService;
import com.unipay.service.settings.UserSettingsService;
import com.unipay.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private UserProfileService userProfileService;
    @Mock private UserSettingsService userSettingsService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void create_shouldCallAllServicesAndReturnUser() {
        UserRegisterCommand command = mock(UserRegisterCommand.class);
        ProfileCommand profileCommand = mock(ProfileCommand.class);
        UserSettingsCommand settingsCommand = mock(UserSettingsCommand.class);
        User user = User.create(command);
        UserProfile profile = mock(UserProfile.class);
        UserSettings settings = mock(UserSettings.class);

        when(command.getProfile()).thenReturn(profileCommand);
        when(command.getSettings()).thenReturn(settingsCommand);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userProfileService.create(profileCommand, user)).thenReturn(profile);
        when(userSettingsService.create(settingsCommand, user)).thenReturn(settings);

        User result = userService.create(command);

        verify(command).validate();
        verify(userProfileService).create(profileCommand, user);
        verify(userSettingsService).create(settingsCommand, user);
        verify(userRepository, times(2)).save(any(User.class));

        assert result != null;
    }
}
