package com.unipay.security;


import com.unipay.payload.UserDetailsImpl;
import com.unipay.service.session.UserSessionService;
import com.unipay.utils.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class SessionValidationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserSessionService userSessionService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                // validate session ID
                String sessionId = jwtService.extractSessionId(token);
                if (sessionId == null || !userSessionService.isSessionValid(sessionId)) {
                    throw new JwtException("Invalid or expired session");
                }

                // load user details
                String username = jwtService.extractUsername(token);
                UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

                // validate MFA status
                if (userDetails.isMfaRequired() && !userDetails.isMfaVerified()) {
                    throw new JwtException("MFA verification required");
                }

                // set authentication context
                if (jwtService.isTokenValid(token, userDetails)) {
                    setSecurityContextAuthentication(userDetails, request);
                }
            }
        } catch (JwtException | UsernameNotFoundException e) {
            handleError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
    private void handleError(HttpServletResponse response,
                             int statusCode,
                             String message) throws IOException {
        // clear security context
        SecurityContextHolder.clearContext();

        // send standardized error response //
        response.sendError(statusCode, message);

        // optional: Add security headers
        response.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
    }
    private void processJwtToken(String jwt, HttpServletRequest request) {
        try {
            String username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    setSecurityContextAuthentication(userDetails, request);
                }
            }
        } catch (JwtException | UsernameNotFoundException e) {
            logger.error("JWT processing error", e);
        }
    }

    private void setSecurityContextAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
