package com.unipay.exception;

import org.springframework.security.core.AuthenticationException;

public class SessionExpiredException extends AuthenticationException {
    public SessionExpiredException() {
        super("Session has expired");
    }
}