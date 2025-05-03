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
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    /**
     * Creates and saves an audit log entry for the user, recording the action and its details.
     *
     * @param user    The user performing the action.
     * @param action  The action performed by the user.
     * @param details Detailed information about the action.
     */
    @Transactional
    public void createAuditLog(User user, String action, String details) {
        log.debug("Start creating Audit Log for user {}", user.getId());

        // Create the audit log with action, details, and current timestamp
        AuditLog auditLog = AuditLog.create(action, details, LocalDateTime.now());

        // Assign the user to the audit log
        auditLog.setUser(user);

        // Add the audit log to the user's list of audit logs (if required)
        user.addAuditLog(auditLog);

        // Log the successful creation of the audit log
        log.info("Audit Log created successfully with ID {}", auditLog.getId());

        // Save the audit log to the repository
        auditLogRepository.save(auditLog);
    }

    /**
     * Retrieves an audit log either by its ID or by the action it represents.
     *
     * @param auditId The ID of the audit log.
     * @param action  The action associated with the audit log.
     * @return The found audit log.
     * @throws BusinessException if no audit log is found.
     */
    @Override
    @Transactional(readOnly = true)
    public AuditLog findByIdOrAction(String auditId, String action) {
        log.info("Begin fetching Audit Log with id {} and action {}", auditId, action);

        // Try to find the audit log by ID or action, throw exception if not found
        final AuditLog auditLog = auditLogRepository.findByIdOrAction(auditId, action).orElseThrow(
                () -> new BusinessException(ExceptionPayloadFactory.AUDIT_LOG_NOT_FOUND.get())
        );

        // Log the successful fetch
        log.info("Audit Log with id {} fetched successfully", auditId);

        return auditLog;
    }

    /**
     * Retrieves a paginated list of audit logs for a user.
     *
     * @param pageable The pagination parameters.
     * @param userId   The user ID whose audit logs are to be retrieved.
     * @return A paginated list of audit logs.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogsByUserId(Pageable pageable, String userId) {
        log.info("Begin fetching Audit Logs for userId {}", userId);

        // Fetch the audit logs by user ID with pagination
        final Page<AuditLog> auditLogs = auditLogRepository.getAuditLogsByUser_Id(pageable, userId);

        // Log the successful fetch of audit logs
        log.info("Audit Logs fetched successfully for userId {}", userId);

        return auditLogs;
    }
}
