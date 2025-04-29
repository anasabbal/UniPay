package com.unipay.service.audit_log;


import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.AuditLog;
import com.unipay.models.User;
import com.unipay.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        AuditLog auditLog = AuditLog.create(action, details, LocalDateTime.now());
        auditLog.setUser(user);
        user.addAuditLog(auditLog);
        log.info("Audit Log created successfully with ID {}", auditLog.getId());
        auditLogRepository.save(auditLog);
    }
    @Override
    @Transactional(readOnly = true)
    public AuditLog findByIdOrAction(String auditId, String action){
        log.info("Begin fetching Audit Log with id {} and action {}", auditId, action);
        final AuditLog auditLog = auditLogRepository.findByIdOrAction(auditId, action).orElseThrow(
                () -> new BusinessException(ExceptionPayloadFactory.AUDIT_LOG_NOT_FOUND.get())
        );
        log.info("Audit Log with id {} fetched successfully", auditId);
        return auditLog;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogsByUserId(Pageable pageable, String userId){
        log.info("Begin fetching Audit Logs with userId {}", userId);
        final Page<AuditLog> auditLogs = auditLogRepository.getAuditLogsByUser_Id(pageable, userId);
        log.info("Audit Log fetched successfully");
        return auditLogs;
    }
}
