package com.unipay.profile;

import com.unipay.command.ProfileCommand;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.repository.UserProfileRepository;
import com.unipay.service.profile.UserProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    void create_shouldValidateAndPersistProfile() {
        ProfileCommand command = mock(ProfileCommand.class);
        UserProfile profile = mock(UserProfile.class);
        User user = mock(User.class);

        try (MockedStatic<UserProfile> staticMock = mockStatic(UserProfile.class)) {
            staticMock.when(() -> UserProfile.create(command)).thenReturn(profile);
            when(userProfileRepository.save(profile)).thenReturn(profile);

            userProfileService.create(command, user);

            verify(command).validate();
            verify(profile).setUser(user);
            verify(userProfileRepository).save(profile);
        }
    }
}