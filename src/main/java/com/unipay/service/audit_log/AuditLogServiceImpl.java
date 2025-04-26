package com.unipay.service.audit_log;


import com.unipay.models.AuditLog;
import com.unipay.models.User;
import com.unipay.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService{

    private final AuditLogRepository auditLogRepository;

    /**
     * Creates and saves an audit log entry.
     *
     * @param user    The user who performed the action.
     * @param action  The action performed by the user.
     * @param details Detailed information about the action.
     */
    @Transactional
    public void createAuditLog(User user, String action, String details) {
        log.debug("Start creating Audit Log for user {}", user.getId());

        // create a new AuditLog
        AuditLog auditLog = AuditLog.create(action, details, LocalDateTime.now());
        auditLog.setUser(user);
        user.addAuditLog(auditLog);
        log.info("Audit Log created successfully with ID {}", auditLog.getId());
        auditLogRepository.save(auditLog);
    }
}
