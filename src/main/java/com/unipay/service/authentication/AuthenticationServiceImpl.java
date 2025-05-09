package com.unipay.service.authentication;


import com.unipay.annotation.Auditable;
import com.unipay.command.LoginCommand;
import com.unipay.command.UserRegisterCommand;
import com.unipay.enums.AuditLogAction;
import com.unipay.enums.UserStatus;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.User;
import com.unipay.models.UserSession;
import com.unipay.payload.UserDetailsImpl;
import com.unipay.repository.UserRepository;
import com.unipay.response.LoginResponse;
import com.unipay.security.UserDetailsServiceImpl;
import com.unipay.service.audit_log.AuditLogService;
import com.unipay.service.login_histroy.LoginHistoryService;
import com.unipay.service.mfa.MFAService;
import com.unipay.service.session.UserSessionService;
import com.unipay.service.user.UserService;
import com.unipay.utils.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final MFAService mfaService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final UserSessionService userSessionService;
    private final LoginHistoryService loginHistoryService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;



    /**
     * Registers a new user by calling the UserService and creating a registration record.
     *
     * @param command The registration command containing the user's information.
     * @param request The HTTP request, used to fetch session-related information.
     */
    @Override
    @Auditable(action = "USER_REGISTRATION")
    public void register(UserRegisterCommand command, HttpServletRequest request) {
        userService.create(command, request);
    }

    /**
     * Authenticates the user based on their login credentials and manages MFA flow if enabled.
     *
     * @param command The login command containing user credentials.
     * @param request The HTTP request, used to fetch session-related information.
     * @return A login response containing the authentication tokens or an error response.
     */
    @Override
    @Transactional
    @Auditable(action = "USER_LOGIN")
    public LoginResponse login(LoginCommand command, HttpServletRequest request) {
        try {
            Authentication authentication = attemptAuthentication(command);
            User user = getAuthenticatedUser(authentication);
            validateUserStatus(user);

            // Handle MFA flow
            if (user.getMfaSettings() != null && user.getMfaSettings().isEnabled()) {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                String challengeToken = jwtService.generateMfaChallengeToken(userDetails);
                return LoginResponse.mfaRequired(challengeToken);
            }

            UserSession session = createUserSession(user, request);
            logSuccessfulLogin(user, request);
            return createLoginResponse((UserDetailsImpl) authentication.getPrincipal(), session);

        } catch (DisabledException e) {
            handleAuthenticationFailure(command.getEmail(), request, "Account disabled",
                    ExceptionPayloadFactory.USER_NOT_ACTIVE);
        } catch (BadCredentialsException e) {
            handleAuthenticationFailure(command.getEmail(), request, "Invalid credentials",
                    ExceptionPayloadFactory.INVALID_PAYLOAD);
        } catch (AuthenticationException e) {
            handleAuthenticationFailure(command.getEmail(), request, "Authentication failure",
                    ExceptionPayloadFactory.AUTHENTICATION_FAILED);
        }
        return LoginResponse.error();
    }
    /**
     * Initiates password reset workflow by delegating to UserService.
     *
     * @param email the email address of the user requesting reset
     * @param request the HTTP request for logging purposes
     */
    @Override
    @Auditable(action = "PASSWORD_RESET_REQUEST")
    @Transactional
    public void forgotPassword(String email, HttpServletRequest request) {
        log.info("Forgot password requested for [{}]", email);
        // Delegate to UserService which handles token creation, persistence, and email dispatch
        userService.forgotPassword(email);
        // Record audit log
        auditLogService.createAuditLog(
                userService.findByEmailWithRolesAndPermissions(email),
                AuditLogAction.PASSWORD_RESET_REQUEST.getAction(),
                "Password reset requested"
        );
    }
    /**
     * Verifies the MFA code and finalizes the user authentication process.
     *
     * @param challengeToken The MFA challenge token.
     * @param code The MFA verification code.
     * @param request The HTTP request to fetch session-related information.
     * @return A login response containing the authentication tokens or an error response.
     */
    @Override
    @Transactional
    @Auditable(action = "MFA_VERIFICATION")
    public LoginResponse verifyMfa(String challengeToken, String code, HttpServletRequest request) {
        try {
            // Validate challenge token format
            if (!jwtService.isMfaChallengeToken(challengeToken)) {
                throw new BusinessException(ExceptionPayloadFactory.INVALID_MFA_CHALLENGE.get());
            }

            // Extract user from challenge token
            String email = jwtService.extractUsername(challengeToken);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get()));

            // Validate MFA code
            if (!mfaService.validateCode(user, code)) {
                handleAuthenticationFailure(email, request, "Invalid MFA code",
                        ExceptionPayloadFactory.INVALID_MFA_CODE);
            }

            // Create session and mark MFA verified
            UserSession session = createUserSession(user, request);
            logSuccessfulLogin(user, request);

            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
            userDetails.setMfaVerified(true);

            return createLoginResponse(userDetails, session);

        } catch (JwtException e) {
            throw new BusinessException(ExceptionPayloadFactory.INVALID_MFA_CHALLENGE.get());
        }
    }
    /**
     * Refreshes the authentication token using the provided refresh token.
     *
     * @param refreshToken The refresh token provided by the client.
     * @param request The HTTP request to fetch session-related information.
     * @return A login response containing new authentication tokens.
     */
    @Override
    @Transactional
    @Auditable(action = "TOKEN_REFRESH")
    public LoginResponse refreshToken(String refreshToken, HttpServletRequest request) {
        try {
            // Validate refresh token format
            if (!jwtService.isRefreshToken(refreshToken)) {
                throw new BusinessException(ExceptionPayloadFactory.INVALID_TOKEN.get());
            }

            // Extract session ID and validate
            String sessionId = jwtService.extractSessionId(refreshToken);
            if (!userSessionService.isSessionValid(sessionId)) {
                throw new BusinessException(ExceptionPayloadFactory.INVALID_SESSION.get());
            }

            // Get user details
            String email = jwtService.extractUsername(refreshToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

            // Generate new token pair
            return LoginResponse.success(
                    jwtService.generateTokenPair(userDetails, sessionId),
                    userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList())
            );

        } catch (JwtException | UsernameNotFoundException e) {
            throw new BusinessException(ExceptionPayloadFactory.INVALID_TOKEN.get());
        }
    }
    /**
     * Retrieves the current authenticated user from the security context.
     *
     * @return The currently authenticated user.
     */
    @Override
    @Transactional
    public User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", authentication);
        return userService.findByEmailWithRolesAndPermissions(authentication.getName());
    }
    private UserSession createUserSession(User user, HttpServletRequest request) {
        UserSession session = userSessionService.createSession(
                user,
                request.getHeader("User-Agent"),
                request.getRemoteAddr()
        );
        user.getSessions().add(session);
        return session;
    }
    private Authentication attemptAuthentication(LoginCommand command) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        command.getEmail(),
                        command.getPassword()
                )
        );
    }
    private User getAuthenticatedUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get()));
    }
    private void validateUserStatus(User user) {
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new DisabledException("User account is not active");
        }
    }
    private void logSuccessfulLogin(User user, HttpServletRequest request) {
        loginHistoryService.createLoginHistory(user, request, true);
        auditLogService.createAuditLog(
                user,
                AuditLogAction.LOGIN_SUCCESS.getAction(),
                "Successful login"
        );
    }
    private LoginResponse createLoginResponse(UserDetailsImpl userDetails, UserSession session) {
        return LoginResponse.success(
                jwtService.generateTokenPair(userDetails, session.getId()),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }
    private void handleAuthenticationFailure(String email, HttpServletRequest request,
                                             String reason, ExceptionPayloadFactory payload) {
        userRepository.findByEmail(email).ifPresent(user -> {
            loginHistoryService.createLoginHistory(user, request, false);
            auditLogService.createAuditLog(user,
                    AuditLogAction.LOGIN_FAILED.getAction(),
                    "Failed login attempt: " + reason
            );

        });
        throw new BusinessException(payload.get());
    }
}