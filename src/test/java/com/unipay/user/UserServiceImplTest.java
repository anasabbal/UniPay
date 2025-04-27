package com.unipay.user;

import com.unipay.command.ProfileCommand;
import com.unipay.command.UserRegisterCommand;
import com.unipay.command.UserSettingsCommand;
import com.unipay.exception.BusinessException;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserRepository;
import com.unipay.service.audit_log.AuditLogService;
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

    /*@Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private UserSettingsService userSettingsService;

    @Mock
    private LoginHistoryService loginHistoryService;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private HttpServletRequest mockRequest;

    private UserRegisterCommand mockCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockCommand = createMockCommand();
    }

    private UserRegisterCommand createMockCommand() {
        UserRegisterCommand command = mock(UserRegisterCommand.class);
        when(command.getUsername()).thenReturn("testuser");
        when(command.getEmail()).thenReturn("testuser@example.com");
        when(command.getProfile()).thenReturn(new ProfileCommand());
        when(command.getSettings()).thenReturn(new UserSettingsCommand());
        return command;
    }

    @Test
    void testCreateUserSuccess() {
        // Arrange
        User mockUser = mock(User.class);
        when(userRepository.existsByEmailOrUsername(any(), any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        mockProfileAndSettingsMocks();

        // Act
        User createdUser = userService.create(mockCommand, mockRequest);

        // Assert
        assertNotNull(createdUser);
        verifyCreateUserInteractions(mockUser);
    }

    @Test
    void testCreateUserWithExistingEmailOrUsername() {
        // Arrange
        when(userRepository.existsByEmailOrUsername(any(), any())).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.create(mockCommand, mockRequest);
        });
        assertEquals("user.already.exists", exception.getMessage());
    }

    @Test
    void testCreateUserThrowsExceptionDuringProfileSettingsAssociation() {
        // Arrange
        when(userRepository.existsByEmailOrUsername(any(), any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mock(User.class));
        doThrow(new RuntimeException("Error")).when(userProfileService).create(any(), any());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.create(mockCommand, mockRequest);
        });
        assertTrue(exception.getMessage().contains("Error creating and associating user profile/settings"));
    }

    @Test
    void testCreateUserThrowsExceptionDuringAuditLogCreation() {
        // Arrange
        when(userRepository.existsByEmailOrUsername(any(), any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mock(User.class));
        mockProfileAndSettingsMocks();
        doThrow(new RuntimeException("Error")).when(auditLogService).createAuditLog(any(), any(), any());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.create(mockCommand, mockRequest);
        });
        assertTrue(exception.getMessage().contains("Error creating audit log for user"));
    }

    private void mockProfileAndSettingsMocks() {
        when(userProfileService.create(any(), any(User.class))).thenReturn(new UserProfile());
        when(userSettingsService.create(any(), any(User.class))).thenReturn(new UserSettings());
    }

    private void verifyCreateUserInteractions(User mockUser) {
        verify(userRepository).save(mockUser);
        verify(userProfileService).create(any(), eq(mockUser));
        verify(userSettingsService).create(any(), eq(mockUser));
        verify(loginHistoryService).createLoginHistory(eq(mockUser), eq(mockRequest), eq(true));
        verify(auditLogService).createAuditLog(eq(mockUser), anyString(), anyString());
    }*/
}
