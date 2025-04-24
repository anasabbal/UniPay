package com.unipay.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;



/**
 * Represents a role within the system, defining a set of permissions that can be assigned to users.
 * Roles facilitate role-based access control (RBAC) by grouping permissions and associating them with users.
 *
 * <p>Key Fields:
 * <ul>
 *   <li><strong>name</strong>: The unique name of the role (e.g., "ADMIN", "USER").</li>
 *   <li><strong>description</strong>: A brief description of the role's purpose.</li>
 *   <li><strong>userRoles</strong>: The set of user-role associations linking users to this role.</li>
 *   <li><strong>permissions</strong>: The set of permissions associated with this role.</li>
 * </ul>
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    private String name;
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}

