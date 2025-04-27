package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents an audit log entry that records user actions within the system.
 * Each entry captures the user who performed the action, the nature of the action,
 * detailed information about the action, and the timestamp when it occurred.
 * This entity is essential for tracking changes and ensuring accountability.
 *
 * @see User
 */
@Setter
@Entity
@Getter
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity {

    /**
     * The user who performed the action recorded in the audit log.
     * This is a many-to-one relationship with the `User` entity, as multiple actions
     * can be attributed to a single user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * A short description or title of the action performed.
     * This could include things like "User Registered", "Password Changed", etc.
     */
    private String action;

    /**
     * Detailed information regarding the action performed.
     * This can include data such as the specific changes made or additional context
     * for the action.
     */
    private String details;

    /**
     * The timestamp of when the action occurred.
     * This field captures the exact date and time the action was performed.
     */
    private LocalDateTime timestamp;

    public static AuditLog create(final String action, final String details, final LocalDateTime timestamp){
        final AuditLog auditLog = new AuditLog();

        auditLog.action = action;
        auditLog.details = details;
        auditLog.timestamp = timestamp;

        return auditLog;
    }
}
