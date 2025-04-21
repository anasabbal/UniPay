package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


/**
 * Represents a record of a user's login attempt within the system.
 * Captures essential details such as the timestamp of the login,
 * the IP address from which the attempt was made, the user agent
 * string of the client's browser or application, and whether the
 * login attempt was successful.
 *
 * This entity aids in monitoring user access patterns, detecting
 * unauthorized access attempts, and auditing user authentication
 * activities.
 */
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
