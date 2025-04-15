package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

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
