package com.unipay.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidMfaCodeException extends AuthenticationException {
    public InvalidMfaCodeException() {
        super("Invalid MFA code");
    }
}