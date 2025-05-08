package com.unipay.service.audit_log;

import com.unipay.models.AuditLog;
import com.unipay.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing audit logs in the system.
 * It defines methods to create, retrieve, and manage audit logs.
 */
public interface AuditLogService {

    /**
     * Creates and saves an audit log entry for the specified user.
     *
     * @param user    The user who performed the action.
     * @param action  The action performed by the user.
     * @param details Detailed information about the action.
     */
    void createAuditLog(User user, String action, String details);

    /**
     * Retrieves an audit log by its ID or action.
     *
     * @param auditId The ID of the audit log.
     * @param action  The action associated with the audit log.
     * @return The found audit log.
     */
    AuditLog findByIdOrAction(String auditId, String action);

    /**
     * Retrieves a paginated list of audit logs for a user.
     *
     * @param pageable The pagination parameters.
     * @param userId   The user ID whose audit logs are to be retrieved.
     * @return A paginated list of audit logs.
     */
    Page<AuditLog> getAuditLogsByUserId(Pageable pageable, String userId);
}
