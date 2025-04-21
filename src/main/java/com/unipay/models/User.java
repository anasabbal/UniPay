package com.unipay.models;

import com.unipay.enums.UserStatus;
import jakarta.persistence.*;


import java.util.Set;



/**
 * Represents a user account within the system, encompassing authentication credentials,
 * profile information, roles, login history, settings, security questions, and audit logs.
 *
 * <p>Key Fields:
 * <ul>
 *   <li><strong>username</strong>: The unique username for the user.</li>
 *   <li><strong>email</strong>: The unique email address associated with the user.</li>
 *   <li><strong>passwordHash</strong>: The hashed password for authentication.</li>
 *   <li><strong>status</strong>: The current status of the user account (e.g., ACTIVE, INACTIVE, SUSPENDED).</li>
 *   <li><strong>profile</strong>: The user's profile containing personal information.</li>
 *   <li><strong>roles</strong>: The set of roles assigned to the user.</li>
 *   <li><strong>loginHistories</strong>: Records of the user's login attempts.</li>
 *   <li><strong>settings</strong>: User-specific settings and preferences.</li>
 *   <li><strong>securityQuestions</strong>: Security questions for account recovery.</li>
 *   <li><strong>auditLogs</strong>: Logs of actions performed by the user within the system.</li>
 * </ul>
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserStatus status; // ACTIVE, INACTIVE, SUSPENDED

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<LoginHistory> loginHistories;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserSettings settings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<SecurityQuestion> securityQuestions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<AuditLog> auditLogs;
}
