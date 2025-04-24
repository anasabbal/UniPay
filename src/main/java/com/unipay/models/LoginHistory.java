package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@Entity
@Table(name = "login_history")
public class LoginHistory extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime loginTimestamp;
    private String ipAddress;
    private String userAgent;
    private boolean successful;
}
