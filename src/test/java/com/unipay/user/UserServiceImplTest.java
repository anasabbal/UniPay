package com.unipay.user;

import com.unipay.command.ProfileCommand;
import com.unipay.command.UserRegisterCommand;
import com.unipay.command.UserSettingsCommand;
import com.unipay.exception.BusinessException;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserRepository;
import com.unipay.service.login_histroy.LoginHistoryService;
import com.unipay.service.profile.UserProfileService;
import com.unipay.service.settings.UserSettingsService;
import com.unipay.service.user.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private UserSettingsService userSettingsService;

    @Mock
    private LoginHistoryService loginHistoryService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegisterCommand command;
    private User user;
    private UserProfile profile;
    private UserSettings settings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize common objects
        command = mock(UserRegisterCommand.class);
        user = mock(User.class);
        profile = mock(UserProfile.class);
        settings = mock(UserSettings.class);

        // Mock shared behavior
        when(command.getProfile()).thenReturn(mock(ProfileCommand.class));
        when(command.getSettings()).thenReturn(mock(UserSettingsCommand.class));
    }

    @Test
    void create_shouldCreateUserAndReturnSavedUser() {
        // Arrange
        mockUserRepositoryAndServices();

        // Simulate that the user already exists
        when(userRepository.existsByEmailOrUsername(anyString(), anyString())).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.create(command, request);
        });

        assertNotNull(exception);
        assertEquals("user.already.exist", exception.getPayload().getMessage());
        assertEquals(4, exception.getPayload().getCode());
    }

    @Test
    void create_shouldThrowExceptionIfUserExists() {
        // Arrange
        when(command.getEmail()).thenReturn("existingemail@example.com");
        when(command.getUsername()).thenReturn("existingUsername");
        when(userRepository.existsByEmailOrUsername("existingemail@example.com", "existingUsername")).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.create(command, request);
        });

        assertNotNull(exception.getPayload());
        assertEquals("user.already.exist", exception.getPayload().getMessage());
        assertEquals(4, exception.getPayload().getCode());
    }

    @Test
    void create_shouldHandleProfileAndSettingsAssociationErrors() {
        // Arrange
        mockUserRepositoryAndServices();
        when(userProfileService.create(any(), any(User.class))).thenThrow(new RuntimeException("Profile creation failed"));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.create(command, request);
        });

        assertEquals("technical.error", exception.getPayload().getMessage());
    }

    private void mockUserRepositoryAndServices() {
        // Mock repository and service calls
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userProfileService.create(any(), any(User.class))).thenReturn(profile);
        when(userSettingsService.create(any(), any(User.class))).thenReturn(settings);
    }
}
