package com.unipay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;


@Component
public class UserIdAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final WebSecurity webSecurity;

    @Autowired
    public UserIdAuthorizationManager(WebSecurity webSecurity) {
        this.webSecurity = webSecurity;
    }

    @Override
    public AuthorizationDecision check(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext context) {

        try {
            boolean granted = webSecurity.checkUserId(
                    authentication.get(),
                    context.getVariables().get("userId"),
                    context.getRequest()
            );
            return new AuthorizationDecision(granted);
        } catch (Exception e) {
            return new AuthorizationDecision(false);
        }
    }
}