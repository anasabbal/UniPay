package com.unipay.exception;

import org.springframework.security.core.AuthenticationException;

public class MfaVerificationRequiredException extends AuthenticationException {
    public MfaVerificationRequiredException() {
        super("MFA verification required");
    }
}