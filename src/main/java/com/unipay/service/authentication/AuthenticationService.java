package com.unipay.service.authentication;

import com.unipay.command.LoginCommand;
import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import com.unipay.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Service interface for handling authentication-related operations.
 * This includes user registration, login, MFA verification, and token refresh.
 */
public interface AuthenticationService {

    /**
     * Registers a new user in the system.
     *
     * @param command The registration command containing the user data.
     * @param request The HTTP request to get additional session info.
     */
    void register(UserRegisterCommand command, HttpServletRequest request);

    /**
     * Logs a user in based on the provided login command.
     *
     * @param command The login command containing user credentials.
     * @param request The HTTP request to get session info.
     * @return The login response containing the token and user roles.
     */
    LoginResponse login(LoginCommand command, HttpServletRequest request);

    /**
     * Retrieves the current authenticated user from the security context.
     *
     * @return The current authenticated user.
     */
    User getCurrentUser();

    /**
     * Verifies the MFA challenge and performs the necessary actions to authenticate the user.
     *
     * @param challengeToken The token representing the MFA challenge.
     * @param code The MFA code entered by the user.
     * @param request The HTTP request to get session info.
     * @return The login response containing the token and user roles.
     */
    LoginResponse verifyMfa(String challengeToken, String code, HttpServletRequest request);

    /**
     * Refreshes the authentication token based on the provided refresh token.
     *
     * @param refreshToken The refresh token.
     * @param request The HTTP request to get session info.
     * @return The login response containing the new tokens and user roles.
     */
    LoginResponse refreshToken(String refreshToken, HttpServletRequest request);
    void forgotPassword(String email, HttpServletRequest request);
    void logout(HttpServletRequest request);
}
