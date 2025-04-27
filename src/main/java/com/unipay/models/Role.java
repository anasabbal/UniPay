package com.unipay.models;

import com.unipay.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a role within the system, defining a set of permissions that can be assigned to users.
 * Roles facilitate role-based access control (RBAC) by grouping permissions and associating them with users.
 *
 * <p>Key Features:
 * <ul>
 *   <li><strong>name</strong>: The unique name of the role (e.g., "ADMIN", "USER").</li>
 *   <li><strong>description</strong>: A brief description of the role's purpose.</li>
 *   <li><strong>userRoles</strong>: The set of user-role associations linking users to this role.</li>
 *   <li><strong>permissions</strong>: The set of permissions associated with this role.</li>
 * </ul>
 *
 * <p>Roles allow the system to assign specific rights and duties to users based on their role within the organization.
 * For example, an "ADMIN" role might have the permission to manage user accounts, whereas a "USER" role may only have access to personal information.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    /**
     * The unique name of the role (e.g., "ADMIN", "USER").
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;

    /**
     * A brief description of the role's purpose.
     */
    private String description;

    /**
     * The set of user-role associations linking users to this role.
     * This relationship supports many-to-many associations between users and roles.
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    /**
     * The set of permissions associated with this role.
     * This is a many-to-many relationship with the {@link Permission} entity.
     */
    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    public static Role create(RoleName name, String description) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        role.setUserRoles(new HashSet<>());
        role.setPermissions(new HashSet<>());
        return role;
    }
}
