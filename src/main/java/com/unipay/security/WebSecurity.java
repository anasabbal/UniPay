package com.unipay.security;


import com.unipay.payload.UserDetailsImpl;
import com.unipay.service.mfa.MFAService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSecurity {

    private final MFAService mfaService;

    public boolean checkUserId(Authentication authentication, String userId, HttpServletRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // ID mismatch check
        if (!userDetails.getId().equals(userId)) {
            return false;
        }

        // MFA state validation
        if (requiresMfaVerification(request)) {
            return userDetails.isMfaVerified();
        }

        return hasRequiredRole(userDetails);
    }

    private boolean requiresMfaVerification(HttpServletRequest request) {
        return !request.getRequestURI().contains("/mfa/verify") &&
                !request.getRequestURI().contains("/mfa/qrcode");
    }

    private boolean hasRequiredRole(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
    }
}

