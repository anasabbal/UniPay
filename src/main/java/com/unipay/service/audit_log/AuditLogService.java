package com.unipay.service.audit_log;

import com.unipay.models.User;

public interface AuditLogService {
    void createAuditLog(User user, String action, String details);
}
