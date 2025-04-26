package com.unipay.aspect;


import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.data.domain.Auditable;
import org.springframework.stereotype.Component;

@Component
public class SecurityAuditAspect {

    @AfterReturning(
            pointcut = "@annotation(auditable)",
            returning = "result"
    )
    public void auditSuccess(Auditable auditable, Object result) {
        // Log successful security events
    }

    @AfterThrowing(
            pointcut = "@annotation(auditable)",
            throwing = "ex"
    )
    public void auditFailure(Auditable auditable, Exception ex) {
        // Log failed security attempts
    }
}
