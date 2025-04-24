package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


/**
 * Represents an audit log entry that records user actions within the system.
 * Each entry captures the user who performed the action, the nature of the action,
 * detailed information about the action, and the timestamp when it occurred.
 * This entity is essential for tracking changes and ensuring accountability.
 */
@Entity
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action;
    private String details;
    private LocalDateTime timestamp;
}
