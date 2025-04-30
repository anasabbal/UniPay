package com.unipay.service.authentication;


import com.unipay.annotation.Auditable;
import com.unipay.command.LoginCommand;
import com.unipay.command.UserRegisterCommand;
import com.unipay.enums.AuditLogAction;
import com.unipay.enums.UserStatus;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.User;
import com.unipay.payload.UserDetailsImpl;
import com.unipay.repository.UserRepository;
import com.unipay.response.LoginResponse;
import com.unipay.service.audit_log.AuditLogService;
import com.unipay.service.login_histroy.LoginHistoryService;
import com.unipay.service.user.UserService;
import com.unipay.utils.JwtService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final LoginHistoryService loginHistoryService;
    private final AuthenticationManager authenticationManager;


    @Override
    @Auditable(action = "USER_REGISTRATION")
    public User register(UserRegisterCommand command, HttpServletRequest request) {
        return userService.create(command, request);
    }

    @Override
    @Transactional
    @Auditable(action = "USER_LOGIN")
    public LoginResponse login(LoginCommand command, HttpServletRequest request) {
        try {
            Authentication authentication = attemptAuthentication(command);
            User user = getAuthenticatedUser(authentication);
            validateUserStatus(user);

            logSuccessfulLogin(user, request);
            return createLoginResponse(authentication);

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
        throw new BusinessException(ExceptionPayloadFactory.AUTHENTICATION_FAILED.get());
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

    private LoginResponse createLoginResponse(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new LoginResponse(
                jwtService.generateTokenPair(userDetails),
                roles
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