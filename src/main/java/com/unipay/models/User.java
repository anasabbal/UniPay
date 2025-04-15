package com.unipay.models;

import com.unipay.enums.UserStatus;
import jakarta.persistence.*;

import java.util.Set;

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
