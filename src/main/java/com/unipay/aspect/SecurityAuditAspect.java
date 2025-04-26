package com.unipay.aspect;

import com.unipay.annotation.Auditable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SecurityAuditAspect {

    @AfterReturning(
            pointcut = "@annotation(auditable)",
            returning = "result"
    )
    public void auditSuccess(JoinPoint joinPoint, Auditable auditable, Object result) {
        log.info("✅ SECURITY SUCCESS: Action={} Method={} Result={}",
                auditable.action(),
                joinPoint.getSignature().toShortString(),
                result
        );
    }

    @AfterThrowing(
            pointcut = "@annotation(auditable)",
            throwing = "ex"
    )
    public void auditFailure(JoinPoint joinPoint, Auditable auditable, Exception ex) {
        log.error("❌ SECURITY FAILURE: Action={} Method={} Exception={}",
                auditable.action(),
                joinPoint.getSignature().toShortString(),
                ex.getMessage()
        );
    }
}
