package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * Represents the association between a user and a role in the system.
 * Supports many-to-many relationship via this join table.
 *
 * <p>Key Fields:
 * <ul>
 *   <li><strong>user</strong>: The user to whom the role is assigned.</li>
 *   <li><strong>role</strong>: The assigned role.</li>
 *   <li><strong>assignedAt</strong>: Timestamp indicating when the role was assigned.</li>
 * </ul>
 */
@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    private LocalDateTime assignedAt;
}
