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
import com.unipay.service.user.UserServiceImpl;
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
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final LoginHistoryService loginHistoryService;
    private final AuditLogService auditLogService;
    private final UserService userService;


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
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            User user = userRepository.findByEmail(command.getEmail())
                    .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get()));

            if (user.getStatus() != UserStatus.ACTIVE) {
                throw new BusinessException(ExceptionPayloadFactory.USER_NOT_ACTIVE.get());
            }

            String jwtToken = jwtService.generateAccessToken(userDetails);

            loginHistoryService.createLoginHistory(user, request, true);
            auditLogService.createAuditLog(
                    user,
                    AuditLogAction.LOGIN_SUCCESS.getAction(),
                    "User logged in successfully."
            );

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new LoginResponse(jwtToken, roles);

        } catch (DisabledException e) {
            handleFailedLogin(command.getEmail(), request, "Account not active", AuditLogAction.LOGIN_FAILED);
            throw new BusinessException(ExceptionPayloadFactory.USER_NOT_ACTIVE.get());
        } catch (BadCredentialsException e) {
            handleFailedLogin(command.getEmail(), request, "Invalid credentials", AuditLogAction.LOGIN_FAILED);
            throw new BusinessException(ExceptionPayloadFactory.INVALID_PAYLOAD.get());
        } catch (AuthenticationException e) {
            handleFailedLogin(command.getEmail(), request, "Authentication failed", AuditLogAction.LOGIN_FAILED);
            throw new BusinessException(ExceptionPayloadFactory.AUTHENTICATION_FAILED.get());
        }
    }
    private void handleFailedLogin(String email, HttpServletRequest request, String details, AuditLogAction action) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            loginHistoryService.createLoginHistory(user, request, false);
            auditLogService.createAuditLog(user, action.getAction(), details);
        }
    }
}
