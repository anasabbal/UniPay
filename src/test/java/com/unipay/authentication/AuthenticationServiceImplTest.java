package com.unipay.authentication;


import com.unipay.command.LoginCommand;
import com.unipay.enums.AuditLogAction;
import com.unipay.enums.UserStatus;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.User;
import com.unipay.payload.UserDetailsImpl;
import com.unipay.repository.UserRepository;
import com.unipay.response.LoginResponse;
import com.unipay.service.audit_log.AuditLogService;
import com.unipay.service.authentication.AuthenticationServiceImpl;
import com.unipay.service.login_histroy.LoginHistoryService;
import com.unipay.service.user.UserService;
import com.unipay.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

   /* @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuditLogService auditLogService;
    @Mock
    private LoginHistoryService loginHistoryService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private final String testEmail = "test@unipay.com";
    private final String testPassword = "validPassword";
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .email(testEmail)
                .passwordHash("hashedPassword")
                .status(UserStatus.ACTIVE)
                .build();
    }

    @Test
    void login_SuccessfulAuthentication_ReturnsValidResponse() {
        // Arrange
        LoginCommand command = new LoginCommand(testEmail, testPassword);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailsImpl(testUser),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);
        when(userRepository.findByEmail(testEmail))
                .thenReturn(Optional.of(testUser));
        when(jwtService.generateTokenPair(any()))
                .thenReturn(new JwtService.TokenPair("access", "refresh"));

        // Act
        LoginResponse response = authenticationService.login(command, request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.roles().size());
        assertEquals("ROLE_USER", response.roles().get(0));

        verify(loginHistoryService).createLoginHistory(testUser, request, true);
        verify(auditLogService).createAuditLog(
                testUser,
                AuditLogAction.LOGIN_SUCCESS.getAction(),
                "Successful login"
        );
    }

    @Test
    void login_InvalidCredentials_ThrowsBusinessException() {
        // Arrange
        LoginCommand command = new LoginCommand(testEmail, "wrongPassword");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> authenticationService.login(command, request));

        assertEquals(ExceptionPayloadFactory.INVALID_PAYLOAD.get(), exception.getExceptionPayload());
        verify(loginHistoryService, never()).createLoginHistory(any(), any(), eq(true));
        verify(auditLogService).createAuditLog(any(), eq(AuditLogAction.LOGIN_FAILED.getAction()), anyString());
    }

    @Test
    void login_DisabledAccount_ThrowsBusinessException() {
        // Arrange
        testUser.setStatus(UserStatus.SUSPENDED);
        LoginCommand command = new LoginCommand(testEmail, testPassword);
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(
                        new UserDetailsImpl(testUser), null));
        when(userRepository.findByEmail(testEmail))
                .thenReturn(Optional.of(testUser));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> authenticationService.login(command, request));

        assertEquals(ExceptionPayloadFactory.USER_NOT_ACTIVE.get(), exception.getExceptionPayload());
        verify(loginHistoryService).createLoginHistory(testUser, request, false);
    }

    @Test
    void login_AuthenticationFailure_LogsAppropriateAudit() {
        // Arrange
        LoginCommand command = new LoginCommand(testEmail, testPassword);
        when(authenticationManager.authenticate(any()))
                .thenThrow(new DisabledException("Account disabled"));

        // Act & Assert
        assertThrows(BusinessException.class,
                () -> authenticationService.login(command, request));

        verify(auditLogService).createAuditLog(
                any(),
                eq(AuditLogAction.LOGIN_FAILED.getAction()),
                contains("Account disabled")
        );
    }

    @Test
    void validateUserStatus_ActiveUser_NoExceptionThrown() {
        testUser.setStatus(UserStatus.ACTIVE);
        assertDoesNotThrow(() -> authenticationService.validateUserStatus(testUser));
    }

    @Test
    void validateUserStatus_SuspendedUser_ThrowsDisabledException() {
        testUser.setStatus(UserStatus.SUSPENDED);
        assertThrows(DisabledException.class,
                () -> authenticationService.validateUserStatus(testUser));
    }

    @Test
    void getAuthenticatedUser_ValidAuthentication_ReturnsUser() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl(testUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null);
        when(userRepository.findByEmail(testEmail))
                .thenReturn(Optional.of(testUser));

        // Act
        User result = authenticationService.getAuthenticatedUser(authentication);

        // Assert
        assertEquals(testUser, result);
    }

    @Test
    void handleAuthenticationFailure_ExistingUser_LogsFailure() {
        // Arrange
        when(userRepository.findByEmail(testEmail))
                .thenReturn(Optional.of(testUser));

        // Act
        authenticationService.handleAuthenticationFailure(
                testEmail,
                request,
                "Test reason",
                ExceptionPayloadFactory.AUTHENTICATION_FAILED
        );

        // Assert
        verify(loginHistoryService).createLoginHistory(testUser, request, false);
        verify(auditLogService).createAuditLog(
                testUser,
                AuditLogAction.LOGIN_FAILED.getAction(),
                "Failed login attempt: Test reason"
        );
    }*/
}