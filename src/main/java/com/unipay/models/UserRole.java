package com.unipay.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents the association between a user and a role in the system.
 * Supports many-to-many relationship via this join table.
 *
 * <p>Key Features:
 * <ul>
 *   <li><strong>user</strong>: The user to whom the role is assigned.</li>
 *   <li><strong>role</strong>: The assigned role.</li>
 *   <li><strong>assignedAt</strong>: Timestamp indicating when the role was assigned.</li>
 * </ul>
 *
 * <p>This entity facilitates role-based access control (RBAC) by linking users to roles, which in turn determine what
 * permissions the user has within the system.
 */
@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends BaseEntity {
    /**
     * The user to whom the role is assigned.
     * This relationship ties the role to a specific user in the system.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The assigned role.
     * Each role defines a set of permissions that the user can perform.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * Timestamp indicating when the role was assigned.
     * This helps track when the user was granted this role.
     */
    private LocalDateTime assignedAt;
}
