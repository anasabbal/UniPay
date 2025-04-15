package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_history")
public class LoginHistory extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime loginTimestamp;
    private String ipAddress;
    private String userAgent;
    private boolean successful;
}
