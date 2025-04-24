package com.unipay.profile;

import com.unipay.command.ProfileCommand;
import com.unipay.models.UserProfile;
import com.unipay.repository.UserProfileRepository;
import com.unipay.service.profile.UserProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldValidateAndSaveUserProfile() {
        ProfileCommand command = mock(ProfileCommand.class);
        UserProfile profile = mock(UserProfile.class);

        mockStatic(UserProfile.class).when(() -> UserProfile.create(command)).thenReturn(profile);
        when(userProfileRepository.save(profile)).thenReturn(profile);

        userProfileService.create(command);

        verify(command).validate();
        verify(userProfileRepository).save(profile);
    }
}
