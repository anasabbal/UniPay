package com.unipay.service.audit_log;

import com.unipay.models.AuditLog;
import com.unipay.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogService {
    void createAuditLog(User user, String action, String details);
    AuditLog findByIdOrAction(String auditId, String action);
    Page<AuditLog> getAuditLogsByUserId(Pageable pageable, String userId);
}
